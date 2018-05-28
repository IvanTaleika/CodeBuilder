package cb.core.ui.design.operations;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import cb.core.ui.design.operations.factories.ExpandItemFactory;
import cb.core.ui.design.operations.factories.OperationButtonFactory;
import cb.core.ui.design.widgets.Operation;

public class OperationsComposite {
  private Group operationsGroup;

  public OperationsComposite(Composite parent) {
    operationsGroup = new Group(parent, SWT.NONE);
    //TODO get title from xml
    operationsGroup.setText(OperationWidgetsText.Operations_title);
    // TODO use factory for layouts?
    GridLayout operationsGridLayout = new GridLayout(1, false);
    operationsGridLayout.horizontalSpacing = 0;
    operationsGridLayout.marginHeight = 0;
    operationsGridLayout.verticalSpacing = 0;
    operationsGridLayout.marginWidth = 0;
    operationsGroup.setLayout(operationsGridLayout);
    ScrolledComposite scrolledComposite = new ScrolledComposite(operationsGroup, SWT.V_SCROLL);
    scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    scrolledComposite.setExpandHorizontal(true);
    scrolledComposite.setExpandVertical(true);
    
    ExpandBar operationsExpandBar = new ExpandBar(operationsGroup, SWT.NONE);
    // TODO foreach expandIten in xml
    ExpandItem expandItem = ExpandItemFactory.create(operationsExpandBar);
    Composite composite = new Composite(operationsExpandBar, SWT.NONE);
    //TODO change composite background
    expandItem.setControl(composite);
    // TODO foreach button in xml
    Operation button = OperationButtonFactory.create(composite);

  }
}
