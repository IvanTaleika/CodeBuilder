package cb.core.editors.designEditor;

import java.io.File;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.internal.ui.dialogs.TypeSelectionDialog2;
import org.eclipse.jdt.ui.dialogs.TypeSelectionExtension;
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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import cb.core.exceptions.CBResourceException;
import cb.core.ui.design.operations.OperationsView;
import cb.core.ui.design.operations.components.IOperation;
import cb.core.ui.design.operations.components.IOperationListener;
import cb.core.ui.design.structure.ClassSummaryView;
import cb.core.ui.design.structure.MethodTreeView;
import cb.core.ui.design.structure.dialogs.AddMethodDialog;
import cb.core.ui.utils.BundleResourceProvider;
import cb.core.utils.PathProvider;


//This class should use JavaCodeGenerator and write result into the file
public class DesignEditor extends EditorPart implements IOperationListener {
  // TODO do I need to hold parent?
  private IOperation selectedOperation;
  private File templateFile;
  // TODO convert to local?
  private OperationsView operationsView;
  private MethodTreeView methodTreeView;
  private ClassSummaryView classSummaryView;

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

      methodTreeView = new MethodTreeView(structureSashForm);
      methodTreeView.buildGUI();

      classSummaryView = new ClassSummaryView(structureSashForm);
      classSummaryView.buildGUI();
      try {

        templateFile = BundleResourceProvider.getFile(PathProvider.getTemplateClasspath());
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
      //TODO move exception messages to specific file
      Label errorLabel = new Label(parent, SWT.NONE);
      errorLabel.setText("Plugin resources error:\n" + e.getMessage());
    }


  }

  @Override
  public void setFocus() {
    // TODO Auto-generated method stub

  }
  
  // TODO Do I need to subscribe?
  @Override
  public void operationSelected(IOperation iOperation) {
    if(selectedOperation == iOperation) {
      selectedOperation = null;
      return;
    }
    if (selectedOperation != null) {
      selectedOperation.setSelection(false);
    }
    selectedOperation = iOperation;
  }

}
