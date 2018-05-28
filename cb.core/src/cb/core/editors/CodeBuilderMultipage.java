package cb.core.editors;
//TODO use properties and resources, not strings like ""
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import cb.core.editors.designEditor.DesignEditor;
import cb.core.editors.sourceEditor.SourceEditor;

public class CodeBuilderMultipage extends MultiPageEditorPart {
  private SourceEditor sourceEditor;
  private DesignEditor designEditor;


  public CodeBuilderMultipage() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    if(!input.getName().matches(EditorsMessages.Multipage_inputRegularExpretionPattern)) {
      throw new PartInitException(EditorsMessages.Multipage_inputError);
    }
    super.init(site, input);
  }
  
  @Override
  // @PostConstruct
  // // TODO check PostCinstruct
  protected void createPages() {
    createSourcePage();
    createDesignPage();

  }

  private void createSourcePage() {
    try {
      sourceEditor = new SourceEditor();
      // TODO What is getEditorInput(), getTitle()?
      int index = addPage(sourceEditor, getEditorInput());
      // TODO define "Source" in .properties
      setPageText(index, EditorsMessages.Multipage_FirstPage);
    } catch (PartInitException e) {
      ErrorDialog.openError(getSite().getShell(), EditorsMessages.Multipage_creationError, null,
          e.getStatus());
    }
  }

  private void createDesignPage() {

    try {
      designEditor = new DesignEditor();
      // TODO What is getEditorInput(), getTitle()?
      int index = addPage(designEditor, getEditorInput());
      // TODO define "Design" in .properties
      setPageText(index, EditorsMessages.Multipage_SecondPage);
    } catch (PartInitException e) {
      ErrorDialog.openError(getSite().getShell(), EditorsMessages.Multipage_creationError, null,
          e.getStatus());
    }

  }

  @Override
  public void doSave(IProgressMonitor monitor) {
    // TODO Auto-generated method stub

  }

  @Override
  public void doSaveAs() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isSaveAsAllowed() {
    // TODO Auto-generated method stub
    return false;
  }

}
