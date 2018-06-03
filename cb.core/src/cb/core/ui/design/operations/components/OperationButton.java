package cb.core.ui.design.operations.components;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.w3c.dom.Element;
import cb.core.exceptions.CBResourceException;


//TODO think about Custom Button implementation
public class OperationButton extends Operation implements IOperationWidget {
  private Button uiOperation;
  
  public OperationButton() {

  }

  @Override
  public boolean isSelected() {
    return uiOperation.getSelection();
  }

  @Override
  public void setSelection(boolean isSelected) {
    uiOperation.setSelection(isSelected);

  }
  
  public Button getUI() {
    return uiOperation;
  }
  
  private void notifyListeners() {
    for (IOperationListener iOperationListener : getListeners()) {
      iOperationListener.operationSelected(this);
    }
  }

  @Override
  public Button buildUI(Composite parent, int style) {
    uiOperation = new Button(parent, style);
    uiOperation.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        notifyListeners();
      }
    });
    return uiOperation;
  }
  
  @Override
  public void setName(String name) {
    super.setName(name);
    uiOperation.setText(name);
  }

  @Override
  public void setIcon(Image icon) {
    super.setIcon(icon);
    uiOperation.setImage(icon);
  }

}
