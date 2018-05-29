package cb.core.ui.design.operations;

import java.util.LinkedList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import cb.core.ui.design.operations.components.Operation;
import cb.core.ui.design.operations.components.OperationListener;
import cb.core.ui.design.operations.components.factories.ExpandItemFactory;
import cb.core.ui.design.operations.components.factories.OperationButtonFactory;
import cb.core.ui.utils.GridLayoutFactory;

public class OperationsController {
  private Composite uiParent;
  private Group operationsGroup;
  private LinkedList<Operation> operations;

  public OperationsController(Composite parent) {
    operations = new LinkedList<>();
    uiParent = parent;

  }


  public void listenOperations(OperationListener listener) {
    for (Operation operation : operations) {
      operation.addListener(listener);
    }
  }

  public void removeOperationListener(OperationListener listener) {
    for (Operation operation : operations) {
      operation.removeListener(listener);
    }
  }

  public Group getUI() {
    return operationsGroup;
  }

  public void buildGUI() {
    operationsGroup = new Group(uiParent, SWT.NONE);
    // TODO get title from xml
    operationsGroup.setText(OperationsControllerMessages.OperationsController_title);
    operationsGroup.setLayout(GridLayoutFactory.create(1, false, 0, 0));

    ScrolledComposite scrolledComposite = new ScrolledComposite(operationsGroup, SWT.V_SCROLL);
    scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    scrolledComposite.setExpandHorizontal(true);
    scrolledComposite.setExpandVertical(true);

    ExpandBar operationsExpandBar = new ExpandBar(scrolledComposite, SWT.NONE);
    // TODO foreach expandIten in xml
    ExpandItem expandItem = ExpandItemFactory.create(operationsExpandBar);

    Composite composite = new Composite(operationsExpandBar, SWT.NONE);
    // TODO change composite background
    composite.setLayout(GridLayoutFactory.create(2, false, 1, 1));

    expandItem.setControl(composite);

    // TODO foreach button in xml
    // TODO subscribe
    operations.add(OperationButtonFactory.create(composite));
    // end foreach button

    expandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
    // end foreach item

    scrolledComposite.setContent(operationsExpandBar);
    scrolledComposite.setMinSize(operationsExpandBar.computeSize(SWT.DEFAULT, SWT.DEFAULT));
  }
}
