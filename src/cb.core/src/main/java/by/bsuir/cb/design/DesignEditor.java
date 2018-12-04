package by.bsuir.cb.design;

import by.bsuir.cb.BundleResourceProvider;
import by.bsuir.cb.CodeBuilder;
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
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IInputChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

// TODO add save possibility
// This class should use JavaCodeGenerator and write result into the file
public class DesignEditor extends EditorPart implements IMethodListener {

  private List<IInputChangedListener> listeners;
  private static final String OPERATIONS_PATH = "defaultOperations.xml";
  private static final String GENERATE_IMAGE = "generate.png";
  private static final int[] SASH_FORM_WEIGHT = {120, 150, 250};

  // private List<IMethodTemp> methods;
  private Set<IGenerative> generationSet = new HashSet<>();
  private IContentOutlinePage outlineAdapter;
  private MethodTreeViewer methodTreeViewer;
  // TODO add interface for all method views

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    setInput(input);
    setSite(site);
    // methods = new LinkedList<>();
    listeners = new LinkedList<>();
  }

  @Override
  public void doSave(IProgressMonitor monitor) {
    // TODO implement
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

  private void createToolbar(ViewForm parent) {
    ToolBar mainViewFormToolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
    parent.setTopLeft(mainViewFormToolBar);
    ToolItem addItem = new ToolItem(mainViewFormToolBar, SWT.NONE);
    addItem.setImage(BundleResourceProvider.getImage(GENERATE_IMAGE));
    addItem.setToolTipText(DesignEditorMessages.GenerateButton_ToolTip);
    // addItem.addSelectionListener(new SelectionAdapter() {
    // @Override
    // public void widgetSelected(SelectionEvent e) {
    // if (currentMethod != null) {
    // addMethod();
    // }
    // }
    // });
  }

  @Override
  public void createPartControl(Composite parent) {
    parent.setLayout(new FillLayout());
    ViewForm mainViewForm = new ViewForm(parent, SWT.NONE);
    createToolbar(mainViewForm);

    SashForm shellSashForm = new SashForm(mainViewForm, SWT.BORDER | SWT.SMOOTH);
    mainViewForm.setContent(shellSashForm);

    Group structureGroup = new Group(shellSashForm, SWT.NONE);
    structureGroup.setText(DesignEditorMessages.Structure_Title);
    structureGroup.setLayout(new FillLayout());



    SashForm structureSashForm = new SashForm(structureGroup, SWT.BORDER | SWT.VERTICAL);

    // ClassSummary classSummary = new ClassSummary(structureSashForm);
    // classSummary.buildGui();
    // try {
    // classSummary.updateMethods(getEditorInput());
    // } catch (JavaModelException e1) {
    // // FIXME display error if it occurs
    // e1.printStackTrace();
    // }
    // classSummary.addMethodListener(this);

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
          new Status(Status.WARNING, CodeBuilder.PLUGIN_ID, e.getMessage(), e));
    }

    methodTreeViewer = new MethodTreeViewer();
    methodTreeViewer.createControl(shellSashForm);
    operationPicker.addOperationListener(methodTreeViewer);

    shellSashForm.setWeights(SASH_FORM_WEIGHT);
  }

  // private void addMethod() {
  // try {
  // ICompilationUnit compilationUnit =
  // JavaUI.getWorkingCopyManager().getWorkingCopy(getEditorInput());
  // String code = new Generator().generateCode(currentMethod);
  // code = new Formatter().formateCode(code);
  // IParser parser = new Parser(compilationUnit);
  // parser.insertCode(parser.getInsertPosition(), code);
  // } catch (CbGenerationException exception) {
  // // TODO open error for all exceptions
  // MessageDialog.openWarning(getSite().getShell(), "CodeBuilder error",
  // "Error while generated data: \n" + exception.getMessage());
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }

  // @Override
  // public void methodCreated(String access, String returnType, String name,
  // HashMap<String, String> passedVariables) {
  // currentMethod = new Method(access, returnType, name, passedVariables);
  // methods.add(currentMethod);
  // methodTree.addMethod(currentMethod);
  // }
  //
  // @Override
  // public void methodDeleted(int methodIndex) {
  // methodTree.deleteMethod(methods.remove(methodIndex));
  // }

  // @Override
  // public void valueCreated(int methodIndex, String name, String type) {
  // currentMethod.addVariable(name, type);
  // }
  //
  // @Override
  // public void valueDeleted(int methodIndex, String name, String type) {
  // currentMethod.deleteVariable(name, type);
  // }



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
