package cb.core.ui.design.structure;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;

public class MethodTreeView {
  private Composite uiParent;
  private ViewForm treeViewForm;
  private Composite treeComposite;
  private StackLayout treeCompositeLayout;
  private Tree currentTree;

  public MethodTreeView(Composite uiParent) {
    this.uiParent = uiParent;
  }



  public ViewForm getUI() {
    return treeViewForm;
  }

  public void buildGUI() {


    treeViewForm = new ViewForm(uiParent, SWT.NONE);

    treeComposite = new Composite(treeViewForm, SWT.NONE);
    treeViewForm.setContent(treeComposite);
    treeCompositeLayout = new StackLayout();
    treeComposite.setLayout(treeCompositeLayout);

    CLabel viewFormTitle = new CLabel(treeViewForm, SWT.NONE);
    // TODO set image
    treeViewForm.setTopLeft(viewFormTitle);
    viewFormTitle.setText(StructureViewMessages.MethodTreeView_ViewTitle);
    viewFormTitle.setToolTipText(StructureViewMessages.MethodTreeView_ViewTooltip);

    ToolBar treeViewFormToolBar = new ToolBar(treeViewForm, SWT.FLAT | SWT.RIGHT);
    treeViewForm.setTopRight(treeViewFormToolBar);

    // TODO add toolbarItem listener
    ToolItem expandTreeItem = new ToolItem(treeViewFormToolBar, SWT.NONE);
    // FIXME change to image
    expandTreeItem.setText("a");
    expandTreeItem.setToolTipText(StructureViewMessages.MethodTreeView_ExpandAllToolTip);

    // TODO add toolbarItem listener
    ToolItem collapseTreeItem = new ToolItem(treeViewFormToolBar, SWT.NONE);
    // FIXME change to image
    collapseTreeItem.setText("b");
    collapseTreeItem.setToolTipText(StructureViewMessages.MethodTreeView_CollapseAllToolTip);

  }

}
