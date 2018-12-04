package by.bsuir.cb.design.ui.outline;

import by.bsuir.cb.CodeBuilder;
import by.bsuir.cb.design.DesignEditor;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceManipulation;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IInputChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class DesignEditorOutlinePage extends ContentOutlinePage implements IInputChangedListener {
  private static final ILog LOGGER = CodeBuilder.getDefault().getLog();
  private DesignEditor editor;
  private AddMethodDialog addMethodDialog;
  private AddClassVariableDialog addClassVariableDialog;

  public DesignEditorOutlinePage(DesignEditor editor) {
    this.editor = editor;
  }

  @Override
  public void inputChanged(Object newInput) {
    getTreeViewer().refresh(false);
  }

  private IJavaElement getSelectionElement() {
    return (IJavaElement) ((IStructuredSelection) getTreeViewer().getSelection())
        .getFirstElement();
  }

  @Override
  public void createControl(Composite parent) {
    super.createControl(parent);
    TreeViewer viewer = getTreeViewer();
    viewer.setContentProvider(new DesignEditorOutlineContentProvider());
    viewer.setLabelProvider(new DesignEditorOutlineLabelProvider());
    viewer.setInput(JavaUI.getWorkingCopyManager().getWorkingCopy(editor.getEditorInput()));
    viewer.addDoubleClickListener(
        event -> {
          if (!event.getSelection().isEmpty()) {
            var source = ((IStructuredSelection) event.getSelection())
                .getFirstElement();
            if (source instanceof IMethod) {
              editor.methodSelected((IMethod) source);
            }
          }
        });
    createContextMenu(viewer);
    editor.addInputChangedListener(this);
  }

  private void createContextMenu(Viewer viewer) {
    MenuManager contextMenu = new MenuManager("#DesignEditorOutlineMenu"); //$NON-NLS-1$
    contextMenu.setRemoveAllWhenShown(true);
    contextMenu.addMenuListener(mgr -> fillContextMenu(mgr, viewer));
    Menu menu = contextMenu.createContextMenu(viewer.getControl());
    viewer.getControl().setMenu(menu);
  }


  private void fillContextMenu(IMenuManager contextMenu, Viewer viewer) {

    if (!viewer.getSelection().isEmpty()) {
      contextMenu.add(new DeleteAction(OutlineMessages.Menu_delete));
      contextMenu.add(new AddMethodAction(OutlineMessages.Menu_newMethod));
      contextMenu
          .add(new AddVariableAction(OutlineMessages.Menu_newVariable));
    }
  }


  private final class DeleteAction extends Action {
    private static final String TYPE_STRING = " type ";
    private static final String METHOD_STRING = " method ";
    private static final String FIELD_STRING = " field ";

    private DeleteAction(String text) {
      super(text);
    }

    @Override
    public void run() {
      var selection = getSelectionElement();
      String title = OutlineMessages.DeleteDialog_TitleGeneric;
      String message = OutlineMessages.DeleteDialog_MessageGeneric;
      if (selection instanceof IType) {
        title += TYPE_STRING;
        message += TYPE_STRING;
      } else if (selection instanceof IMethod) {
        title += METHOD_STRING;
        message += METHOD_STRING;
      } else if (selection instanceof IField) {
        title += FIELD_STRING;
        message += FIELD_STRING;
      }
      title += selection.getElementName();
      message += selection.getElementName() + '?';
      if (MessageDialog.openQuestion(editor.getSite().getShell(),
          title, message)) {
        try {
          var parent = selection.getParent();
          ((ISourceManipulation) selection).delete(true, null);
          getTreeViewer().refresh(parent, false);
        } catch (JavaModelException e) {
          LOGGER.log(new Status(e.getStatus().getSeverity(), CodeBuilder.PLUGIN_ID,
              e.getMessage(), e));
        }
        if (selection instanceof IMethod) {
          editor.methodDeleted((IMethod) selection);
        }
      }
    }
  }

  private final class AddVariableAction extends Action {

    private AddVariableAction(String text) {
      super(text);
    }

    @Override
    public void run() {
      if (addClassVariableDialog == null) {
        addClassVariableDialog = new AddClassVariableDialog(editor.getSite().getShell());
      }
      int result = addClassVariableDialog.open();
      if (result == Window.OK) {
        var selectionElement = getSelectionElement();
        try {
          if (selectionElement instanceof IType) {
            ((IType) selectionElement).createField(createFieldContent(), null, false, null);
          } else {
            var parent = ((IType) selectionElement.getParent());
            parent.createField(createFieldContent(), selectionElement, false, null);
            selectionElement = parent;
          }
          getTreeViewer().refresh(selectionElement, false);
        } catch (JavaModelException e) {
          LOGGER.log(new Status(e.getStatus().getSeverity(), CodeBuilder.PLUGIN_ID,
              e.getMessage(), e));
        }
      }
    }

    private String createFieldContent() {
      return addClassVariableDialog.getAccess().toString().toLowerCase() + ' '
          + addClassVariableDialog.getType().getElementName() + ' '
          + addClassVariableDialog.getName()
          + (addClassVariableDialog.getDefaultValue().isEmpty() ? ""
              : " = " + addClassVariableDialog.getDefaultValue())
          + ";\n";
    }
  }

  private final class AddMethodAction extends Action {

    private AddMethodAction(String text) {
      super(text);
    }

    @Override
    public void run() {
      if (addMethodDialog == null) {
        addMethodDialog = new AddMethodDialog(editor.getSite().getShell());
      }
      int result = addMethodDialog.open();
      if (result == Window.OK) {
        var selectionElement = getSelectionElement();
        try {
          if (selectionElement instanceof IType) {
            ((IType) selectionElement).createMethod(createEmptyMethodContent(),
                null, false, null);
          } else {
            var parent = ((IType) selectionElement.getParent());
            parent.createMethod(createEmptyMethodContent(), selectionElement, false, null);
            selectionElement = parent;
          }
          getTreeViewer().refresh(selectionElement, false);
        } catch (JavaModelException e) {
          LOGGER.log(new Status(e.getStatus().getSeverity(), CodeBuilder.PLUGIN_ID,
              e.getMessage(), e));
        }
      }
    }

    private String createEmptyMethodContent() {
      return addMethodDialog.getAccess().toString().toLowerCase() + ' '
          + addMethodDialog.getReturnType().getElementName() + ' ' + addMethodDialog.getName() + '('
          + addMethodDialog.getVariables() + "){ return null;}\n";
    }
  }
}
