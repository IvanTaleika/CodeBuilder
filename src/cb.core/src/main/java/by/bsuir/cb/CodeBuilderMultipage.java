package by.bsuir.cb;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.part.MultiPageEditorPart;
import by.bsuir.cb.design.DesignEditor;

@SuppressWarnings("restriction")
public class CodeBuilderMultipage extends MultiPageEditorPart {
  private IEditorPart sourceEditor;
  private int sourceIndex;
  private DesignEditor designEditor;
  private int designIndex;

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    if (FileValidator.validateExtension(input.getName())) {
      super.init(site, input);
      setPartName(ResourceUtil.getFile(input).getName());
    } else {
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
      sourceIndex = addPage(sourceEditor, getEditorInput());
      setPageText(sourceIndex, MultipageMessages.FirstPage);
    } catch (PartInitException e) {
      ErrorDialog.openError(getSite().getShell(), MultipageMessages.CreationError, null,
          e.getStatus());
    }
  }

  private void createDesignPage() {
    try {
      designEditor = new DesignEditor();
      designIndex = addPage(designEditor, getEditorInput());
      setPageText(designIndex, MultipageMessages.SecondPage);
    } catch (PartInitException e) {
      ErrorDialog.openError(getSite().getShell(), MultipageMessages.CreationError, null,
          e.getStatus());
    }
  }

  @Override
  public void doSave(IProgressMonitor monitor) {
    if (designEditor.isDirty()) {
      saveDesignEditor();
    }
    if (sourceEditor.isDirty()) {
      sourceEditor.doSave(monitor);
    }
  }

  @Override
  public void doSaveAs() {
    // Save as is not allowed
  }

  @Override
  public boolean isSaveAsAllowed() {
    return false;
  }

  @Override
  protected void pageChange(int newPageIndex) {
    if (newPageIndex == sourceIndex && designEditor.isDirty()) {
      saveDesignEditor();
    } else {
      designEditor.inputChanged(sourceEditor.getEditorInput());
    }
    super.pageChange(newPageIndex);
  }

  private void saveDesignEditor() {
    if (MessageDialog.openQuestion(getSite().getShell(), MultipageMessages.SaveTitle,
        MultipageMessages.SaveMessage)) {
      designEditor.doSave(null);
    }
  }

}
