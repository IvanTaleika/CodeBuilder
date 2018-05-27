package cb.core.ui.editors.designPage.operations;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;

public class OperationsComposite {
  private Group operationsGroup;

  public OperationsComposite(Composite parent) {
    operationsGroup = new Group(parent, SWT.NONE);
    operationsGroup.setText(OperationWidgetsText.Operations_title);
    //TODO use factory for layouts?
    operationsGroup.setLayout(new FillLayout());
    ExpandBar operationsExpandBar = new ExpandBar(operationsGroup, SWT.V_SCROLL);
    
  }
}
