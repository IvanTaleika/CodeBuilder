package by.bsuir.cb.design.ui.operation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class OperationButton extends AbstractOperation {
  private Button button;

  public OperationButton() {}

  @Override
  public boolean isSelected() {
    return button.getSelection();
  }

  @Override
  public void setSelection(boolean isSelected) {
    button.setSelection(isSelected);

  }

  @Override
  public Button getUi() {
    return button;
  }

  private void notifyListeners() {
    for (IOperationListener operationListener : getListeners()) {
      operationListener.operationSelected(this);
    }
  }

  @Override
  public Button buildUi(Composite parent) {
    button = new Button(parent, SWT.FLAT | SWT.TOGGLE);
    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        notifyListeners();
      }
    });
    button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    button.setToolTipText(getTooltip());
    button.setImage(getIcon());
    button.setText(getName());
    return button;
  }
}
