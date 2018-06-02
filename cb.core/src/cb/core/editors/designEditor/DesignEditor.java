package cb.core.editors.designEditor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import cb.core.ui.design.DesignModel;


//This class should use JavaCodeGenerator and write result into the file
public class DesignEditor extends EditorPart {
  DesignModel designModel;

  @Override
  public void doSave(IProgressMonitor monitor) {
    // TODO Auto-generated method stub

  }

  @Override
  public void doSaveAs() {
    // TODO Auto-generated method stub

  }

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    // TODO parse page
  }

  @Override
  public boolean isDirty() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isSaveAsAllowed() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void createPartControl(Composite parent) {
    designModel = new DesignModel(parent);
    try {
      designModel.buildGUI();
    } catch (Exception e) {
      // TODO: handle exception
    }


  }

  @Override
  public void setFocus() {
    // TODO Auto-generated method stub

  }

}
