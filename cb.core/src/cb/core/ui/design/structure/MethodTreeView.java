package cb.core.ui.design.structure;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import cb.core.CodeBuilder;
import cb.core.ui.design.IDesignViewPart;

public class MethodTreeView implements IDesignViewPart {
  public final String EXPAND_ALL_IMAGE = "expand_all.gif";
  public final String COLLAPSE_ALL_IMAGE = "collapse_all.gif";

  private Composite uiParent;
  private ViewForm treeViewForm;
  private Composite treeComposite;
  private StackLayout treeCompositeLayout;
  private Tree currentTree;

  public MethodTreeView(Composite uiParent) {
    this.uiParent = uiParent;
  }


  @Override
  public ViewForm getGUI() {
    return treeViewForm;
  }

  @Override
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

    ToolItem expandTreeItem = new ToolItem(treeViewFormToolBar, SWT.NONE);
    expandTreeItem.setImage(CodeBuilder.getImage(EXPAND_ALL_IMAGE));
    expandTreeItem.setToolTipText(StructureViewMessages.MethodTreeView_ExpandAllToolTip);
    expandTreeItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        expandCurrentTree();
      }
    });

    ToolItem collapseTreeItem = new ToolItem(treeViewFormToolBar, SWT.NONE);
    collapseTreeItem.setImage(CodeBuilder.getImage(COLLAPSE_ALL_IMAGE));
    collapseTreeItem.setToolTipText(StructureViewMessages.MethodTreeView_CollapseAllToolTip);
    collapseTreeItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        collapseCurrentTree();
      }
    });
  }

  private void expandCurrentTree() {
    if (currentTree != null) {
      for (TreeItem treeItem : currentTree.getItems()) {
        treeItem.setExpanded(false);
      }
    }
  }

  private void collapseCurrentTree() {
    if (currentTree != null) {
      for (TreeItem treeItem : currentTree.getItems()) {
        treeItem.setExpanded(false);
      }
    }
  }

  @Override
  public void setParent(Composite parent) {
    uiParent = parent;

  }

  @Override
  public Composite getParent() {
    return uiParent;
  }

}
