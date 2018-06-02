package cb.core.ui.design.operations.components;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.w3c.dom.Element;
import cb.core.exceptions.CBResourceException;


//TODO think about Custom Button implementation
public class OperationButton extends AbstractOperation implements OperationWidget {
  private Button uiOperation;
  
  public OperationButton(Composite parent, int style, Element nodeData) throws CBResourceException {
    super(nodeData);
    uiOperation = new Button(parent, style);
    uiOperation.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        notifyListeners();
      }
    });
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
  
  private void notifyListeners() {
    for (IOperationListener iOperationListener : getListeners()) {
      iOperationListener.operationSelected(this);
    }
  }

}
