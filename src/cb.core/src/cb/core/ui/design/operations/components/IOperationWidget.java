package cb.core.ui.design.operations.components;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

public interface IOperationWidget extends IOperation {
  Widget getUI();
  Widget buildUI(Composite parent, int style);
}
