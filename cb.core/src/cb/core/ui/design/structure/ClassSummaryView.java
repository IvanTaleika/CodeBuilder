package cb.core.ui.design.structure;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import cb.core.ui.design.IDesignViewPart;

public class ClassSummaryView implements IDesignViewPart {
  private Composite uiParent;
  private ViewForm summaryViewForm;

  // TODO add tabs here
  public ClassSummaryView(Composite uiParent) {
    this.uiParent = uiParent;
  }



  @Override
  public ViewForm getGUI() {
    return summaryViewForm;
  }

  @Override
  public void buildGUI() {


    summaryViewForm = new ViewForm(uiParent, SWT.NONE);

    CTabFolder summaryTabFolder = new CTabFolder(summaryViewForm, SWT.BORDER);
    summaryViewForm.setContent(summaryTabFolder);

    CTabItem methodsCTabItem = new CTabItem(summaryTabFolder, SWT.NONE);
    methodsCTabItem.setText(StructureViewMessages.ClassSummaryView_1stTabName);
    // TODO add tab content


    CTabItem valuesTabItem = new CTabItem(summaryTabFolder, SWT.NONE);
    valuesTabItem.setText(StructureViewMessages.ClassSummaryView_2ndTabName);
    // TODO add tab content

    CLabel viewFormTitle = new CLabel(summaryViewForm, SWT.NONE);
    // TODO set image
    summaryViewForm.setTopLeft(viewFormTitle);
    viewFormTitle.setText(StructureViewMessages.ClassSummaryView_ViewTitle);
    viewFormTitle.setToolTipText(StructureViewMessages.ClassSummaryView_ViewToolTip);

    ToolBar treeViewFormToolBar = new ToolBar(summaryViewForm, SWT.FLAT | SWT.RIGHT);
    summaryViewForm.setTopRight(treeViewFormToolBar);


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
