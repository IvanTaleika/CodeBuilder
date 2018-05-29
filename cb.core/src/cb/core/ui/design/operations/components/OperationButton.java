package cb.core.ui.design.operations.components;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;


//TODO think about Custom Button implementation
public class OperationButton extends AbstractOperation implements OperationWidget {
  private Button uiOperation;
  
  public OperationButton(Composite parent, int style) {
    uiOperation = new Button(parent, style);
  }

  @Override
  public boolean isSelected() {
    return uiOperation.getSelection();
  }

  @Override
  public void setSelection(boolean isSelected) {
    uiOperation.setSelection(isSelected);
  }
  
  public Widget getUI() {
    return uiOperation;
  }

}
