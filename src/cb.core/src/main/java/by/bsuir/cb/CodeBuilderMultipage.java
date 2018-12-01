package by.bsuir.cb;

import by.bsuir.cb.design.DesignEditor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;


// TODO ADD MultiPageEditorActionBarContributor
// TODO dispose Images
// build.properties
// package org.eclipse.wb.internal.core.editor.multi;
// TODO try to extend FormEditor, IDesignerEditor
// TODO why ICompilationUnit could be null?
public class CodeBuilderMultipage extends MultiPageEditorPart {
  private CompilationUnitEditor sourceEditor;
  private DesignEditor designEditor;

  public CodeBuilderMultipage() {}

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    if (FileValidator.validateExtension(input.getName())) {
      super.init(site, input);
    } else {
      // move Errors messages to global .properties
      throw new PartInitException(MultipageMessages.InputError);
    }
  }

  @Override
  protected void createPages() {
    createSourcePage();
    createDesignPage();
  }

  private void createSourcePage() {
    try {
      sourceEditor = new CompilationUnitEditor();
      int index = addPage(sourceEditor, getEditorInput());
      setPageText(index, MultipageMessages.FirstPage);
    } catch (PartInitException e) {
      ErrorDialog.openError(getSite().getShell(), MultipageMessages.CreationError, null,
          e.getStatus());
    }
  }

  private void createDesignPage() {

    try {
      designEditor = new DesignEditor();
      int index = addPage(designEditor, getEditorInput());
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
