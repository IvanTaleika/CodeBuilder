package cb.core.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.IWorkingCopyManager;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import cb.core.editors.designEditor.DesignEditor;
import cb.core.editors.sourceEditor.SourceEditor;

//build.properties
//package org.eclipse.wb.internal.core.editor.multi;
//TODO try to extend FormEditor, IDesignerEditor
//TODO 1st - add page listener, 2nd - parse source content, 3rd - use default listener for 1st page
//TODO why ICompilationUnit could be null?
public class CodeBuilderMultipage extends MultiPageEditorPart {
  private SourceEditor sourceEditor;
  private DesignEditor designEditor;

  public CodeBuilderMultipage() {

    // TODO Auto-generated constructor stub
  }

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    if (!input.getName().matches(MultipageMessages.InputRegularExpretionPattern)) {
      throw new PartInitException(MultipageMessages.InputError);
    }
    
//    //FIXME
//    //TODO this is here just for tests 
//    IWorkingCopyManager workingCopyManager = JavaUI.getWorkingCopyManager();   
//    ICompilationUnit compilationUnit =  workingCopyManager.getWorkingCopy(input);
//    if(compilationUnit != null) {
//      try {
//        IBuffer buffer = compilationUnit.getBuffer();
//        buffer.append("Vanya vanya molodec");
//      } catch (JavaModelException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//      
//    }
    
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
      setPageText(index, MultipageMessages.FirstPage);
    } catch (PartInitException e) {
      ErrorDialog.openError(getSite().getShell(), MultipageMessages.CreationError, null,
          e.getStatus());
    }
  }

  private void createDesignPage() {

    try {
      designEditor = new DesignEditor();
      // TODO What is getEditorInput(), getTitle()?
      int index = addPage(designEditor, getEditorInput());
      // TODO define "Design" in .properties
      setPageText(index, MultipageMessages.SecondPage);
    } catch (PartInitException e) {
      ErrorDialog.openError(getSite().getShell(), MultipageMessages.CreationError, null,
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
    return true;
  }

}
