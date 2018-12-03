package by.bsuir.cb.design.ui.outline;

import by.bsuir.cb.design.DesignEditor;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.IInputChangedListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class DesignEditorOutlinePage extends ContentOutlinePage implements IInputChangedListener {
  private DesignEditor editor;

  public DesignEditorOutlinePage(DesignEditor editor) {
    this.editor = editor;
  }

  @Override
  public void createControl(Composite parent) {
    super.createControl(parent);
    TreeViewer viewer = getTreeViewer();
    viewer.setContentProvider(new DesignEditorOutlineContentProvider());
    viewer.setLabelProvider(new DesignEditorOutlineLabelProvider());
    viewer.setInput(JavaUI.getWorkingCopyManager().getWorkingCopy(editor.getEditorInput()));
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
      // IStructuredSelection selection = (IStructuredSelection) event.getSelection();
      contextMenu.add(new Action(OutlineMessages.Menu_delete) {
        @Override
        public void run() {
          // viewer.getSelection();
          // viewer.
        }
      });
      contextMenu.add(new Action(OutlineMessages.Menu_newMethod) {
        @Override
        public void run() {
          // JavaUI.getWorkingCopyManager().getWorkingCopy(editor.getEditorInput()).crea
        }
      });
      contextMenu.add(new Action(OutlineMessages.Menu_newVariable) {
        @Override
        public void run() {
          // implement this
        }
      });
    }
  }

  @Override
  public void inputChanged(Object newInput) {
    // TODO Auto-generated method stub

  }


}
