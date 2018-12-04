package by.bsuir.cb.design.ui.method;

import by.bsuir.cb.BundleResourceProvider;
import by.bsuir.cb.design.code.CodeBuilderMethod;
import by.bsuir.cb.design.code.IGenerative;
import by.bsuir.cb.design.code.IScopable;
import by.bsuir.cb.design.code.node.BeginNode;
import by.bsuir.cb.design.code.node.EndNode;
import by.bsuir.cb.design.ui.operation.IOperationListener;
import by.bsuir.cb.design.ui.operation.Operation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class MethodTreeViewer extends ContentOutlinePage
    implements IOperationListener {
  public static final String METHOD_ICON_PATH = "outline/methpub_obj.gif";
  public static final String BEGIN_END_ICON_PATH = "operations/begin_end.gif";
  public static final String ADD_NODE_CURSOR_IMAGE = "treeView/add_cursor.gif";
  private TreeViewDecorator currentMethod;
  private Cursor addNodeCursor;
  private Cursor arrowCursor;
  private Map<IMethod, TreeViewDecorator> managedMethods = new HashMap<>();
  private Operation selectedOperation;

  public Optional<IGenerative> methodChanged(IMethod selection) {
    if (selection == null) {
      currentMethod = null;
    } else {
      currentMethod = managedMethods.computeIfAbsent(selection, this::createMethodDecorator);
    }
    getTreeViewer().setInput(currentMethod);
    getTreeViewer().expandAll();
    return Optional.ofNullable(currentMethod.getSource());
  }

  public Optional<IGenerative> methodDeleted(IMethod selection) {
    var method = managedMethods.remove(selection);
    if (method == currentMethod) {
      currentMethod = null;
      getTreeViewer().setInput(null);
      getTreeViewer().expandAll();
    }
    return Optional.ofNullable(method);
  }

  private TreeViewDecorator createMethodDecorator(IMethod method) {
    var codeBuilderMethod = new CodeBuilderMethod();
    codeBuilderMethod.setJavaMethod(method);
    var beginNode = new TreeViewDecorator();
    beginNode.setSource(new BeginNode(codeBuilderMethod));
    beginNode.setIconPath(BEGIN_END_ICON_PATH);
    beginNode.setName("Begin node");
    var endNode = new TreeViewDecorator();
    endNode.setSource(new EndNode(codeBuilderMethod));
    endNode.setIconPath(BEGIN_END_ICON_PATH);
    endNode.setName("End node");
    codeBuilderMethod.appendChild(beginNode);
    codeBuilderMethod.appendChild(endNode);
    var decorator = new TreeViewDecorator();
    decorator.setIconPath(METHOD_ICON_PATH);
    decorator.setName(method.getElementName());
    decorator.setSource(codeBuilderMethod);
    return decorator;
  }

  @Override
  public void operationSelectionChanged(Operation selection) {
    selectedOperation = selection;
  }

  @Override
  public void selectionChanged(SelectionChangedEvent event) {
    super.selectionChanged(event);
    if (selectedOperation != null && !event.getSelection().isEmpty()) {
      var decorator =
          (TreeViewDecorator) ((IStructuredSelection) event.getSelection()).getFirstElement();
      var source = decorator.getSource();
      var child = new TreeViewDecorator(selectedOperation.getMethodNode(),
          selectedOperation.getIconPath(), selectedOperation.getDescription());

      if (source instanceof CodeBuilderMethod) {
        ((CodeBuilderMethod) source).appendChild(child);
      } else if (source instanceof EndNode) {
        return;
      } else {
        var parent = (IScopable) source.getParent();
        parent.addChild(parent.getChildren().indexOf(decorator) + 1, child);
      }
      ((CodeBuilderMethod) currentMethod.getSource()).setDirty(true);
      getTreeViewer().refresh(false);
    }
  }

  private void createCursors() {
    addNodeCursor = new Cursor(Display.getDefault(),
        BundleResourceProvider.getImage(ADD_NODE_CURSOR_IMAGE).getImageData(), 0, 0);
    arrowCursor = getTreeViewer().getControl().getCursor();
    getTreeViewer().getControl().addDisposeListener(e -> addNodeCursor.dispose());
  }

  @Override
  protected int getTreeStyle() {
    return SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL;
  }

  @Override
  public void createControl(Composite parent) {
    super.createControl(parent);
    createCursors();
    TreeViewer viewer = getTreeViewer();
    viewer.setContentProvider(new MethodTreeViewerContentProvider());
    viewer.setLabelProvider(new MethodTreeViewerLabelProvider());


    viewer.getTree().addMouseTrackListener(new MouseTrackAdapter() {
      @Override
      public void mouseEnter(MouseEvent e) {
        if (selectedOperation != null) {
          viewer.getTree().setCursor(addNodeCursor);
        }
      }

      @Override
      public void mouseExit(MouseEvent e) {
        if (selectedOperation != null) {
          viewer.getTree().setCursor(arrowCursor);
        }
      }
    });
  }

}
