package cb.core.editors.designEditor;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import cb.core.editors.designEditor.method.IMethod;
import cb.core.editors.designEditor.method.IMethodListener;
import cb.core.editors.designEditor.method.Method;
import cb.core.exceptions.CBResourceException;
import cb.core.ui.design.operations.OperationsView;
import cb.core.ui.design.structure.ClassSummaryView;
import cb.core.ui.design.structure.MethodTreeView;
import cb.core.ui.utils.BundleResourceProvider;
import cb.core.utils.PathProvider;


// This class should use JavaCodeGenerator and write result into the file
public class DesignEditor extends EditorPart implements IMethodListener {
  private List<IMethod> methods;
  private IMethod currentMethod;

  private File templateFile;
  // TODO convert to local?
  private OperationsView operationsView;
  private MethodTreeView methodTreeView;
  private ClassSummaryView classSummaryView;
  
  //TODO analyse
  private int[] defaultWeight = {120, 150, 250};

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
    methods = new LinkedList<>();
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
    try {


      // TODO check other layouts
      parent.setLayout(new FillLayout());

      // TODO convert to class-global?
      SashForm shellSashForm = new SashForm(parent, SWT.BORDER | SWT.SMOOTH);

      Group structureGroup = new Group(shellSashForm, SWT.NONE);
      structureGroup.setText(DesignPageMessages.Structure_title);
      // TODO check other layouts
      structureGroup.setLayout(new FillLayout());

      SashForm structureSashForm = new SashForm(structureGroup, SWT.BORDER | SWT.VERTICAL);

      Composite dummy = new Composite(structureSashForm, SWT.BORDER);
      dummy.setLayout(new FillLayout());
      Label dummyLabel = new Label(dummy, SWT.NONE);
      dummyLabel.setText("Coming soon");


      classSummaryView = new ClassSummaryView(structureSashForm);
      classSummaryView.buildGUI();
      classSummaryView.addMethodListener(this);
      try {

        templateFile = BundleResourceProvider.getFile(PathProvider.getTemplateClasspath());
      } catch (Exception e) {
        throw new CBResourceException("Unable to load operations template File");

      }

      operationsView = new OperationsView(shellSashForm, templateFile);
      operationsView.buildGUI();
      
      methodTreeView = new MethodTreeView(shellSashForm);
      methodTreeView.buildGUI();
      operationsView.addOperationsListener(methodTreeView);


      shellSashForm.setWeights(defaultWeight);


    } catch (Exception e) {
      // TODO: create message that something wrong with resources
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

  @Override
  public void setFocus() {
    // TODO Auto-generated method stub

  }


  @Override
  public void methodCreated(String access, String returnType, String name,
      HashMap<String, String> passedVariables) {
    currentMethod = new Method(access, returnType, name, passedVariables);
    methods.add(currentMethod);
    methodTreeView.addMethod(currentMethod);
  }

  @Override
  public void methodDeleted(int methodIndex) {
    methodTreeView.deleteMethod(methods.remove(methodIndex));
  }

  @Override
  public void valueCreated(int methodIndex, String name, String type) {
    currentMethod.addVariable(name, type);
    // TODO send notification to views
  }

  @Override
  public void valueDeleted(int methodIndex, String name, String type) {
    currentMethod.deleteVariable(name, type);
    // TODO send notification to views
  }

  @Override
  public void methodSwitched(int methodIndex) {
    currentMethod = methods.get(methodIndex);
    methodTreeView.switchMethod(methodIndex);
  }

}
