package by.bsuir.cb.design.ui.operation;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

public interface IOperationWidget extends IOperation {
  Widget getUi();

  Widget buildUi(Composite parent, int style);
}
