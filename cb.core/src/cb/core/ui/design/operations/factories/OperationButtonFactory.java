package cb.core.ui.design.operations.factories;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import cb.core.ui.design.widgets.Operation;

public class OperationButtonFactory {

  //TODO add xml Node as an argument
  public static Operation create(Composite parent) {
    Operation button = new Operation(parent, SWT.FLAT | SWT.TOGGLE);
    
    button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    return button;
  }

}
