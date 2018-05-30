package cb.core.ui.design;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import cb.core.ui.design.operations.OperationsController;
import cb.core.ui.design.operations.components.Operation;
import cb.core.ui.design.operations.components.IOperationListener;
import cb.core.ui.design.structure.ClassSummaryView;
import cb.core.ui.design.structure.MethodTreeView;

public class DesignModel implements IOperationListener {
  //TODO do I need to hold source?
  Composite source;
  Operation selectedOperation;
  //TODO convert to local?
  OperationsController operationsController;
  MethodTreeView methodTreeView;
  ClassSummaryView classSummaryView;
  
  public DesignModel(Composite source) {
    this.source = source;
  }
  
  public void buildGUI() {
    //TODO check other layouts
    source.setLayout(new FillLayout());
    
    //TODO convert to class-global?
    SashForm shellSashForm = new SashForm(source, SWT.BORDER | SWT.SMOOTH);
    
    Group structureGroup = new Group(shellSashForm, SWT.NONE);
    structureGroup.setText(DesignModelMessages.Structure_title);
    //TODO check other layouts
    structureGroup.setLayout(new FillLayout());
    
    SashForm structureSashForm = new SashForm(structureGroup, SWT.BORDER | SWT.VERTICAL);
    
    methodTreeView = new MethodTreeView(structureSashForm);
    methodTreeView.buildGUI();
    
    classSummaryView = new ClassSummaryView(structureSashForm);
    classSummaryView.buildGUI();
    
    
    operationsController = new OperationsController(shellSashForm);
    operationsController.buildGUI();
    operationsController.listenOperations(this);
    
    
    ViewForm editorViewForm = new ViewForm(shellSashForm, SWT.NONE);
    
    ToolBar editorToolBar = new ToolBar(editorViewForm, SWT.FLAT | SWT.RIGHT);
    editorViewForm.setTopLeft(editorToolBar);
    
    ToolItem tltmGenerateCode = new ToolItem(editorToolBar, SWT.NONE);
    tltmGenerateCode.setText("Generate code");
    
    Canvas canvas = new Canvas(editorViewForm, SWT.NONE);
    editorViewForm.setContent(canvas);
    shellSashForm.setWeights(new int[] {201, 169, 234});
  }

  //TODO Do I need to subscribe?
  @Override
  public void operationSelected(Operation operation) {
    if(selectedOperation != null) {
      selectedOperation.setSelection(false);;
    }
    selectedOperation = operation;
    
  }

}
