package cb.core.ui.editors.designPage;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class DesignEditor extends EditorPart {

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
    parent.setLayout(new FillLayout(SWT.HORIZONTAL));
    
    SashForm shellSashForm = new SashForm(parent, SWT.BORDER | SWT.SMOOTH);
    
    Group structureGroup = new Group(shellSashForm, SWT.NONE);
    structureGroup.setText("Structure");
    structureGroup.setLayout(new FillLayout(SWT.HORIZONTAL));
    
    SashForm structureSashForm = new SashForm(structureGroup, SWT.VERTICAL);
    
    ViewForm chainViewForm = new ViewForm(structureSashForm, SWT.NONE);
    
    Tree tree = new Tree(chainViewForm, SWT.BORDER | SWT.MULTI);
    chainViewForm.setContent(tree);
    
    TreeItem trtmNewTreeitem = new TreeItem(tree, SWT.NONE);
    trtmNewTreeitem.setText("New TreeItem");
    
    TreeItem trtmNewTreeitem_1 = new TreeItem(trtmNewTreeitem, SWT.NONE);
    trtmNewTreeitem_1.setText("New TreeItem");
    trtmNewTreeitem.setExpanded(true);
    
    Label lblChain = new Label(chainViewForm, SWT.NONE);
    chainViewForm.setTopLeft(lblChain);
    lblChain.setText("Chain");
    
    CTabFolder overviewTabFolder = new CTabFolder(structureSashForm, SWT.BORDER);
    overviewTabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
    
    CTabItem methodsCTabItem = new CTabItem(overviewTabFolder, SWT.NONE);
    methodsCTabItem.setText("Methods");
    
    List list = new List(overviewTabFolder, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
    list.setItems(new String[] {"dasdasd", "dasdasd"});
    methodsCTabItem.setControl(list);
    
    CTabItem valuesTabItem = new CTabItem(overviewTabFolder, SWT.NONE);
    valuesTabItem.setText("Values");
    
    List list_1 = new List(overviewTabFolder, SWT.BORDER);
    valuesTabItem.setControl(list_1);
    structureSashForm.setWeights(new int[] {1, 1});
    
    Group grpOperations = new Group(shellSashForm, SWT.NONE);
    grpOperations.setText("Operations");
    grpOperations.setLayout(new FillLayout(SWT.HORIZONTAL));
    
    ExpandBar expandBar = new ExpandBar(grpOperations, SWT.V_SCROLL);
    
    ExpandItem xpndtmBasic = new ExpandItem(expandBar, SWT.NONE);
    xpndtmBasic.setExpanded(true);
    xpndtmBasic.setText("Basic");
    
    Composite composite = new Composite(expandBar, SWT.NONE);
    xpndtmBasic.setControl(composite);
    xpndtmBasic.setHeight(xpndtmBasic.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
    composite.setLayout(new GridLayout(2, false));
    
    Button btnBegin = new Button(composite, SWT.NONE);
    btnBegin.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    btnBegin.setText("Begin");
    
    Button btnEnd = new Button(composite, SWT.NONE);
    btnEnd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    btnEnd.setText("End");
    
    ExpandItem xpndtmInputoutput = new ExpandItem(expandBar, SWT.NONE);
    xpndtmInputoutput.setText("Input/Output");
    
    ExpandItem xpndtmMinmax = new ExpandItem(expandBar, SWT.NONE);
    xpndtmMinmax.setText("Min/Max");
    
    ExpandItem xpndtmSort = new ExpandItem(expandBar, SWT.NONE);
    xpndtmSort.setText("Sort");
    
    ViewForm editorViewForm = new ViewForm(shellSashForm, SWT.NONE);
    
    ToolBar editorToolBar = new ToolBar(editorViewForm, SWT.FLAT | SWT.RIGHT);
    editorViewForm.setTopLeft(editorToolBar);
    
    ToolItem tltmGenerateCode = new ToolItem(editorToolBar, SWT.NONE);
    tltmGenerateCode.setText("Generate code");
    
    Canvas canvas = new Canvas(editorViewForm, SWT.NONE);
    editorViewForm.setContent(canvas);
    shellSashForm.setWeights(new int[] {201, 169, 234});

  }

  @Override
  public void setFocus() {
    // TODO Auto-generated method stub

  }

}
