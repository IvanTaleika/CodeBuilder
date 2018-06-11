package cb.core.editors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import cb.core.code.utils.CodeUtilsProvider;
import cb.core.editors.designEditor.DesignEditor;
import cb.core.editors.sourceEditor.SourceEditor;
import cb.core.exceptions.CBException;

// TODO ADD MultiPageEditorActionBarContributor
// TODO dispose Images
// build.properties
// package org.eclipse.wb.internal.core.editor.multi;
// TODO try to extend FormEditor, IDesignerEditor
// TODO why ICompilationUnit could be null?
public class CodeBuilderMultipage extends MultiPageEditorPart {
  private SourceEditor sourceEditor;
  private DesignEditor designEditor;
  private final String extensionRegex = "(?i)\\.[0-9a-z]+$";

  public CodeBuilderMultipage() {

    // TODO Auto-generated constructor stub
  }

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    Pattern pattern = Pattern.compile(extensionRegex);
    Matcher matcher = pattern.matcher(input.getName());
    if (matcher.find()) {
      try {
        new CodeUtilsProvider(matcher.group(0));
      } catch (CBException e) {
        throw new PartInitException(e.getMessage());
      }
    } else {
      // move Errors messages to global .properties
      throw new PartInitException(MultipageMessages.InputError);
    }

    super.init(site, input);
  }

  @Override
  protected void createPages() {
    createSourcePage();
    createDesignPage();

  }

  private void createSourcePage() {
    try {
      sourceEditor = new SourceEditor();

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
