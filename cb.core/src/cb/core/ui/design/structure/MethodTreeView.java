package cb.core.ui.design.structure;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import cb.core.CodeBuilder;
import cb.core.editors.designEditor.method.IMethod;
import cb.core.editors.designEditor.node.FunctionNode;
import cb.core.editors.designEditor.node.MethodNode;
import cb.core.editors.designEditor.node.NodeFactory;
import cb.core.ui.design.operations.components.IOperation;
import cb.core.ui.design.operations.components.IOperationListener;
import cb.core.ui.design.structure.dialogs.CustomizeTemplateDialog;

// TODO use TreeView
public class MethodTreeView implements IOperationListener {
  private IOperation selectedOperation;

  public final String BEGIN_ITEM_IMAGE = "operations/icons/begin_end.gif";
  public final String EXPAND_ALL_IMAGE = "expand_all.gif";
  public final String COLLAPSE_ALL_IMAGE = "collapse_all.gif";
  public final String ADD_NODE_CURSOR_IMAGE = "add_cursor.gif";

  private Composite parent;
  private ViewForm treeViewForm;
  private Composite treeComposite;
  private StackLayout treeCompositeLayout;
  private Map.Entry<IMethod, Tree> currentTree;
  private List<TreeItemNodeView> currentTreeNodes;
  private List<Map.Entry<IMethod, Tree>> methodTrees;
  private Map<Map.Entry<IMethod, Tree>, List<TreeItemNodeView>> treeNodesMap;
  private Cursor addNodeCursor;
  private Cursor arrowCursor;

  public MethodTreeView(Composite parent) {
    this.parent = parent;
    methodTrees = new LinkedList<>();
    treeNodesMap = new HashMap<>();
  }


  public ViewForm getGUI() {
    return treeViewForm;
  }

  public void buildGUI() {
    treeViewForm = new ViewForm(parent, SWT.NONE);
    createCursors();


    treeComposite = new Composite(treeViewForm, SWT.NONE);
    treeViewForm.setContent(treeComposite);
    treeCompositeLayout = new StackLayout();
    treeComposite.setLayout(treeCompositeLayout);

    CLabel viewFormTitle = new CLabel(treeViewForm, SWT.NONE);
    // TODO set image
    treeViewForm.setTopLeft(viewFormTitle);
    viewFormTitle.setText(StructureViewMessages.MethodTreeView_ViewTitle);
    viewFormTitle.setToolTipText(StructureViewMessages.MethodTreeView_ViewTooltip);

    ToolBar treeViewFormToolBar = new ToolBar(treeViewForm, SWT.FLAT | SWT.RIGHT);
    treeViewForm.setTopRight(treeViewFormToolBar);

    ToolItem expandTreeItem = new ToolItem(treeViewFormToolBar, SWT.NONE);
    expandTreeItem.setImage(CodeBuilder.getImage(EXPAND_ALL_IMAGE));
    expandTreeItem.setToolTipText(StructureViewMessages.MethodTreeView_ExpandAllToolTip);
    expandTreeItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        expandCurrentTree();
      }
    });

    ToolItem collapseTreeItem = new ToolItem(treeViewFormToolBar, SWT.NONE);
    collapseTreeItem.setImage(CodeBuilder.getImage(COLLAPSE_ALL_IMAGE));
    collapseTreeItem.setToolTipText(StructureViewMessages.MethodTreeView_CollapseAllToolTip);
    collapseTreeItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        collapseCurrentTree();
      }
    });
  }



  public void addMethod(IMethod method) {
    Tree tree = new Tree(treeComposite, SWT.NONE);
    addTreeListeners(tree);

    Map.Entry<IMethod, Tree> methodTree = new AbstractMap.SimpleEntry<IMethod, Tree>(method, tree);
    methodTrees.add(methodTree);
    TreeItemNodeView beginItem = new TreeItemNodeView(method.getBeginNode());
    TreeItem beginTreeItem = new TreeItem(tree, SWT.NONE);
    beginTreeItem.setText(method.getName());
    beginTreeItem.setImage(CodeBuilder.getImage(BEGIN_ITEM_IMAGE));
    beginItem.addNodeTreeItem(beginTreeItem);
    List<TreeItemNodeView> treeNodes = new LinkedList<>();
    treeNodes.add(beginItem);
    currentTree = methodTree;
    treeNodesMap.put(methodTree, treeNodes);
    currentTreeNodes = treeNodes;
    treeCompositeLayout.topControl = tree;
    treeComposite.layout();
  }

  public void switchMethod(int methodIndex) {
    currentTree = methodTrees.get(methodIndex);
    currentTreeNodes = treeNodesMap.get(currentTree);
    treeCompositeLayout.topControl = currentTree.getValue();
    treeComposite.layout();
  }

  public void deleteMethod(IMethod method) {
    Map.Entry<IMethod, Tree> deletedTree = null;
    for (Map.Entry<IMethod, Tree> methodTree : methodTrees) {
      if (methodTree.getKey() == method) {
        deletedTree = methodTree;
        treeNodesMap.remove(methodTree);
        methodTrees.remove(methodTree);
      }
    }
    if (currentTree == deletedTree) {
      currentTree = null;
      currentTreeNodes = null;
      treeCompositeLayout.topControl = null;
      treeComposite.layout();
    }

  }


  public void setParent(Composite parent) {
    this.parent = parent;

  }

  public Composite getParent() {
    return parent;
  }

  @Override
  public void operationSelected(IOperation iOperation) {
    if (selectedOperation == iOperation) {
      selectedOperation = null;
      return;
    }
    if (selectedOperation != null) {
      selectedOperation.setSelection(false);
    }
    selectedOperation = iOperation;
  }


  private void expandCurrentTree() {
    if (currentTree != null) {
      for (TreeItem treeItem : currentTree.getValue().getItems()) {
        treeItem.setExpanded(false);
      }
    }
  }

  private void collapseCurrentTree() {
    if (currentTree != null) {
      for (TreeItem treeItem : currentTree.getValue().getItems()) {
        treeItem.setExpanded(false);
      }
    }
  }

  private void addNewNode(TreeItem selectedItem) {
    for (TreeItemNodeView treeItemNodeView : currentTreeNodes) {
      if (treeItemNodeView.containsNodeTreeItem(selectedItem)) {
        MethodNode selectedNode = treeItemNodeView.getNode();
        switch (selectedNode.getType()) {
          case MethodNode.CONDITION:
            // FIXME add!
            break;
          case MethodNode.FUNCTION:
            // FIXME move childs to new parent
            MethodNode newNode = NodeFactory.create((selectedOperation.getNode()));
            TreeItemNodeView newNodeView = new TreeItemNodeView(newNode);
            ((FunctionNode) selectedNode).addNext(newNode);
            for (TreeItem parent : treeItemNodeView.getNodeTreeItems()) {
              TreeItem nodeTreeItem = new TreeItem(parent, SWT.NONE);
              nodeTreeItem.setText(selectedOperation.getName());
              nodeTreeItem.setImage(selectedOperation.getIcon());
              newNodeView.addNodeTreeItem(nodeTreeItem);
            }
            currentTreeNodes.add(newNodeView);
            break;
          case MethodNode.RETURN:
            MessageDialog.openWarning(parent.getShell(),
                StructureViewMessages.MethodTreeView_AdditingToEndTitle,
                StructureViewMessages.MethodTreeView_AdditingToEndMessage);
            break;
        }
        break;
      }
    }
  }

  private void customizeSelection(TreeItem selectedItem) {
    for (TreeItemNodeView treeItemNodeView : currentTreeNodes) {
      if (treeItemNodeView.containsNodeTreeItem(selectedItem)) {
        if (treeItemNodeView.getNode().getKeywordValueMap() != null) {
          CustomizeTemplateDialog dialog = treeItemNodeView.getCustomizeNodeDialog();
          if (dialog == null) {
            dialog = new CustomizeTemplateDialog(parent.getShell());
            dialog.setNodeKeywordValueMap(treeItemNodeView.getNode().getKeywordValueMap());
            treeItemNodeView.setCustomizeNodeDialog(dialog);
          }
          dialog.setMethodVariables(currentTree.getKey().getVariablesMap());
          dialog.open();
        }
        break;
      }

    }
  }

  private void createCursors() {
    addNodeCursor = new Cursor(Display.getDefault(),
        CodeBuilder.getImage(ADD_NODE_CURSOR_IMAGE).getImageData(), 0, 0);

    arrowCursor = parent.getCursor();
    treeViewForm.addDisposeListener(new DisposeListener() {
      @Override
      public void widgetDisposed(DisposeEvent e) {
        addNodeCursor.dispose();
      }
    });
  }

  private void addTreeListeners(Tree tree) {
    tree.addListener(SWT.MouseDoubleClick, new Listener() {
      @Override
      public void handleEvent(Event event) {
        TreeItem[] selection = currentTree.getValue().getSelection();
        if (selection != null) {
          customizeSelection(selection[0]);
        }
      }

    });
    tree.addMouseTrackListener(new MouseTrackAdapter() {
      @Override
      public void mouseEnter(MouseEvent e) {
        if (selectedOperation != null) {
          tree.setCursor(addNodeCursor);
        }
      }

      @Override
      public void mouseExit(MouseEvent e) {
        if (selectedOperation != null) {
          tree.setCursor(arrowCursor);
        }
      }
    });
    tree.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        TreeItem[] selection = currentTree.getValue().getSelection();
        if (selectedOperation != null && selection != null) {
          addNewNode(selection[0]);
        }
      }

    });
  }

}
