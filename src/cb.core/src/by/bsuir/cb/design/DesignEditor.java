package by.bsuir.cb.design;

import by.bsuir.cb.CodeBuilder;
import by.bsuir.cb.design.code.CbGenerationException;
import by.bsuir.cb.design.code.Formatter;
import by.bsuir.cb.design.code.Generator;
import by.bsuir.cb.design.code.IParser;
import by.bsuir.cb.design.code.Parser;
import by.bsuir.cb.design.code.method.IMethod;
import by.bsuir.cb.design.code.method.IMethodListener;
import by.bsuir.cb.design.code.method.Method;
import by.bsuir.cb.design.ui.BundleResourceProvider;
import by.bsuir.cb.design.ui.operations.OperationPicker;
import by.bsuir.cb.design.ui.structure.ClassSummary;
import by.bsuir.cb.design.ui.structure.MethodTree;
import by.bsuir.cb.utils.PathProvider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.ui.IWorkingCopyManager;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

// TODO add save possibility
// This class should use JavaCodeGenerator and write result into the file
public class DesignEditor extends EditorPart implements IMethodListener {
  private static final String GENERATE_IMAGE = "generate.png";
  private static final int[] SASH_FORM_WEIGHT = {120, 150, 250};

  private List<IMethod> methods;
  private IMethod currentMethod;
  // TODO add interface for all method views
  private OperationPicker operationPicker;
  private MethodTree methodTree;
  private ClassSummary classSummary;
  private ICompilationUnit compilationUnit;
  private IWorkbenchPartSite partSite;


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
    methods = new LinkedList<>();
    partSite = site;
    // TODO move it to addMethod class.
    // use getMethodInput(), but compilationUnit can be null then.
    IWorkingCopyManager workingCopyManager = JavaUI.getWorkingCopyManager();
    compilationUnit = workingCopyManager.getWorkingCopy(input);
    // TODO May be there might be the way to use DI
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
  public void setFocus() {
    // TODO Auto-generated method stub
  }

  private void createToolbar(ViewForm parent) {
    ToolBar mainViewFormToolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
    parent.setTopLeft(mainViewFormToolBar);
    ToolItem addItem = new ToolItem(mainViewFormToolBar, SWT.NONE);
    addItem.setImage(CodeBuilder.getImage(GENERATE_IMAGE));
    addItem.setToolTipText(DesignPageMessages.GenerateButton_ToolTip);
    addItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (currentMethod != null) {
          addMethod();
        }
      }
    });
  }

  @Override
  public void createPartControl(Composite parent) {
    try {
      parent.setLayout(new FillLayout());
      ViewForm mainViewForm = new ViewForm(parent, SWT.NONE);
      createToolbar(mainViewForm);

      SashForm shellSashForm = new SashForm(mainViewForm, SWT.BORDER | SWT.SMOOTH);
      mainViewForm.setContent(shellSashForm);

      Group structureGroup = new Group(shellSashForm, SWT.NONE);
      structureGroup.setText(DesignPageMessages.Structure_title);
      structureGroup.setLayout(new FillLayout());

      SashForm structureSashForm = new SashForm(structureGroup, SWT.BORDER | SWT.VERTICAL);

      Composite dummy = new Composite(structureSashForm, SWT.BORDER);
      dummy.setLayout(new FillLayout());
      Label dummyLabel = new Label(dummy, SWT.NONE);
      dummyLabel.setText("Coming soon");

      classSummary = new ClassSummary(structureSashForm);
      classSummary.buildGui();
      classSummary.addMethodListener(this);
      File templateFile;
      try {
        templateFile = BundleResourceProvider.getFile(PathProvider.getTemplateClasspath());
      } catch (IOException e) {
        throw new CbResourceException("Unable to load operations template File");
      }

      operationPicker = new OperationPicker(shellSashForm, templateFile);
      operationPicker.buildGui();

      methodTree = new MethodTree(shellSashForm);
      methodTree.buildGui();
      operationPicker.addOperationsListener(methodTree);

      shellSashForm.setWeights(SASH_FORM_WEIGHT);

    } catch (Exception e) {
      for (Control control : parent.getChildren()) {
        if (!control.isDisposed()) {
          control.dispose();
        }
      }
      // TODO move exception messages to specific file
      Label errorLabel = new Label(parent, SWT.NONE);
      errorLabel.setText("Plugin resources error:\n" + e.getMessage());
    }
  }

  private void addMethod() {
    try {
      String code = new Generator().generateCode(currentMethod);
      code = new Formatter().formateCode(code);
      IParser parser = new Parser(compilationUnit);
      parser.insertCode(parser.getInsertPosition(), code);
    } catch (CbGenerationException exception) {
      // TODO open error for all exceptions
      MessageDialog.openWarning(getSite().getShell(), "CodeBuilder error",
          "Error while generated data: \n" + exception.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void methodCreated(String access, String returnType, String name,
      HashMap<String, String> passedVariables) {
    currentMethod = new Method(access, returnType, name, passedVariables);
    methods.add(currentMethod);
    methodTree.addMethod(currentMethod);
  }

  @Override
  public void methodDeleted(int methodIndex) {
    methodTree.deleteMethod(methods.remove(methodIndex));
  }

  @Override
  public void valueCreated(int methodIndex, String name, String type) {
    currentMethod.addVariable(name, type);
  }

  @Override
  public void valueDeleted(int methodIndex, String name, String type) {
    currentMethod.deleteVariable(name, type);
  }

  @Override
  public void methodSwitched(int methodIndex) {
    currentMethod = methods.get(methodIndex);
    methodTree.switchMethod(methodIndex);
  }

  // TODO what is this? Note: this solve error with nullpointer exception
  @Override
  public IWorkbenchPartSite getSite() {
    return partSite;
  }


}
