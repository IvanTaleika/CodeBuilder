package by.bsuir.cb.design;

import by.bsuir.cb.CodeBuilder;
import by.bsuir.cb.design.code.Formatter;
import by.bsuir.cb.design.code.IGenerative;
import by.bsuir.cb.design.code.IMethodListener;
import by.bsuir.cb.design.ui.method.MethodTreeViewer;
import by.bsuir.cb.design.ui.operation.OperationPicker;
import by.bsuir.cb.design.ui.operation.xml.XmlParsingException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IInputChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class DesignEditor extends EditorPart implements IMethodListener {

  private List<IInputChangedListener> listeners;
  private static final String OPERATIONS_PATH = "defaultOperations.xml";
  private static final int[] SASH_FORM_WEIGHT = {120, 150, 250};

  private Set<IGenerative> generationSet = new HashSet<>();
  private IContentOutlinePage outlineAdapter;
  private MethodTreeViewer methodTreeViewer;

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    setInput(input);
    setSite(site);
    listeners = new LinkedList<>();
  }

  @Override
  public void doSave(IProgressMonitor monitor) {
    try {
      var type = JavaUI.getWorkingCopyManager().getWorkingCopy(getEditorInput()).getTypes()[0];
      Formatter formatter = new Formatter();
      for (IGenerative g : generationSet) {
        var code = g.toCodeString();
        code = formatter.formateCode(code);
        type.createMethod(code, null, true, null);
        g.setDirty(false);
      }
    } catch (JavaModelException e) {
      ErrorDialog.openError(getSite().getShell(), "Generation error", e.getMessage(),
          new Status(e.getStatus().getSeverity(), CodeBuilder.PLUGIN_ID, e.getMessage(), e));
    }
  }

  @Override
  public void doSaveAs() {
    throw new UnsupportedOperationException("Save as is disabled for this view");
  }

  @Override
  public boolean isDirty() {
    return generationSet.stream().anyMatch(IGenerative::isDirty);
  }

  @Override
  public boolean isSaveAsAllowed() {
    return false;
  }

  @Override
  public void setFocus() {
    outlineAdapter.setFocus();
  }



  @Override
  public void createPartControl(Composite parent) {
    parent.setLayout(new FillLayout());
    ViewForm mainViewForm = new ViewForm(parent, SWT.NONE);

    SashForm shellSashForm = new SashForm(mainViewForm, SWT.BORDER | SWT.SMOOTH);
    mainViewForm.setContent(shellSashForm);

    Group structureGroup = new Group(shellSashForm, SWT.NONE);
    structureGroup.setText(DesignEditorMessages.Structure_Title);
    structureGroup.setLayout(new FillLayout());



    SashForm structureSashForm = new SashForm(structureGroup, SWT.BORDER | SWT.VERTICAL);

    outlineAdapter = getAdapter(IContentOutlinePage.class);
    outlineAdapter.createControl(structureSashForm);
    outlineAdapter.setFocus();


    OperationPicker operationPicker = new OperationPicker(shellSashForm);
    var operationsXml = getClass().getResourceAsStream(OPERATIONS_PATH);
    try {
      operationPicker.loadOperations(operationsXml);
    } catch (XmlParsingException | NullPointerException e) {
      ErrorDialog.openError(getSite().getShell(),
          DesignEditorMessages.XmlOperationsErrorDialog_Title,
          DesignEditorMessages.XmlOperationsErrorDialog_Message,
          new Status(IStatus.WARNING, CodeBuilder.PLUGIN_ID, e.getMessage(), e));
    }

    methodTreeViewer = new MethodTreeViewer(this);
    methodTreeViewer.createControl(shellSashForm);
    operationPicker.addOperationListener(methodTreeViewer);

    shellSashForm.setWeights(SASH_FORM_WEIGHT);
  }


  /**
   * Calls when source file is modified by source editor.
   * 
   * @param input new editor input
   */
  public void inputChanged(IEditorInput input) {
    setInput(input);
    for (IInputChangedListener listener : listeners) {
      listener.inputChanged(input);
    }
  }

  public void addInputChangedListener(IInputChangedListener listener) {
    listeners.add(listener);
  }

  public void removeInputChangedListener(IInputChangedListener listener) {
    listeners.remove(listener);
  }

  @Override
  public void methodSelected(IMethod method) {
    methodTreeViewer.methodChanged(method).ifPresent(g -> generationSet.add(g));

  }

  @Override
  public void methodDeleted(IMethod method) {
    methodTreeViewer.methodDeleted(method).ifPresent(g -> generationSet.remove(g));
  }
}
