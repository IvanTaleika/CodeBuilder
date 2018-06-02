package cb.core.ui.design;

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import cb.core.exceptions.CBResourceException;
import cb.core.ui.design.operations.OperationsView;
import cb.core.ui.design.operations.components.IOperationListener;
import cb.core.ui.design.operations.components.Operation;
import cb.core.ui.design.structure.ClassSummaryView;
import cb.core.ui.design.structure.MethodTreeView;
import cb.core.ui.utils.BundleResourceProvider;

public class DesignModel implements IOperationListener {
  // TODO do I need to hold parent?
  private Composite parent;
  private Operation selectedOperation;
  private File templateFile;
  // TODO convert to local?
  private OperationsView operationsView;
  private MethodTreeView methodTreeView;
  private ClassSummaryView classSummaryView;

  // TODO analyze code parent extension and get path from specific class
  private final String templateFileClasspath = "resources/operations/operationsJava.xml";

  public DesignModel(Composite source) {
    this.parent = source;
  }

  public void buildGUI() {
    try {


      // TODO check other layouts
      parent.setLayout(new FillLayout());

      // TODO convert to class-global?
      SashForm shellSashForm = new SashForm(parent, SWT.BORDER | SWT.SMOOTH);

      Group structureGroup = new Group(shellSashForm, SWT.NONE);
      structureGroup.setText(DesignModelMessages.Structure_title);
      // TODO check other layouts
      structureGroup.setLayout(new FillLayout());

      SashForm structureSashForm = new SashForm(structureGroup, SWT.BORDER | SWT.VERTICAL);

      methodTreeView = new MethodTreeView(structureSashForm);
      methodTreeView.buildGUI();

      classSummaryView = new ClassSummaryView(structureSashForm);
      classSummaryView.buildGUI();
      try {

        templateFile = BundleResourceProvider.getFile(templateFileClasspath);
      } catch (Exception e) {
        throw new CBResourceException("Unable to load operations template File");

      }

      operationsView = new OperationsView(shellSashForm, templateFile);
      operationsView.buildGUI();


      ViewForm editorViewForm = new ViewForm(shellSashForm, SWT.NONE);

      ToolBar editorToolBar = new ToolBar(editorViewForm, SWT.FLAT | SWT.RIGHT);
      editorViewForm.setTopLeft(editorToolBar);

      ToolItem tltmGenerateCode = new ToolItem(editorToolBar, SWT.NONE);
      tltmGenerateCode.setText("Generate code");

      Canvas canvas = new Canvas(editorViewForm, SWT.NONE);
      editorViewForm.setContent(canvas);
      shellSashForm.setWeights(new int[] {201, 169, 234});
      
      
      operationsView.addOperationsListener(this);
    } catch (Exception e) {
      // TODO: create message that something wrong with resources
      for(Control control : parent.getChildren()) {
        if(!control.isDisposed()) {
          control.dispose();
        }
      }
      //TODO move message to specific file
      Label errorLabel = new Label(parent, SWT.NONE);
      errorLabel.setText("Plugin resources error:\n" + e.getMessage());
    }
  }

  // TODO Do I need to subscribe?
  @Override
  public void operationSelected(Operation operation) {
    if(selectedOperation == operation) {
      selectedOperation = null;
      return;
    }
    if (selectedOperation != null) {
      selectedOperation.setSelection(false);
    }
    selectedOperation = operation;

  }

}
