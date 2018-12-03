package by.bsuir.cb.design.ui.outline;

import by.bsuir.cb.design.code.AccessModifyer;
import java.util.Arrays;
import lombok.Getter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddClassVariableDialog extends AddVariableDialog {
  @Getter
  private AccessModifyer access;
  @Getter
  private String defaultValue;
  private Text defaultValueText;
  private Combo accessCombo;

  public AddClassVariableDialog(Shell parentShell) {
    super(parentShell);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    super.createDialogArea(parent);
    Composite container = getContainer();
    createAccessModifyerCombo(container);
    createDefaultValueText(container);
    return null;
  }

  private void createAccessModifyerCombo(Composite container) {
    Label accessLabel = new Label(container, SWT.NONE);
    accessLabel.setText(OutlineMessages.AccessLabel);
    accessCombo = new Combo(container, SWT.READ_ONLY);
    accessCombo.setItems(
        Arrays.stream(AccessModifyer.values()).map(m -> m.toString().toLowerCase())
            .toArray(String[]::new));
    GridData gridData = new GridData();
    gridData.horizontalSpan = 2;
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    accessCombo.setLayoutData(gridData);
    accessCombo.addModifyListener(e -> getButton(OK).setEnabled(isOkEnabled()));
  }

  private void createDefaultValueText(Composite container) {
    Label defaultValueLabel = new Label(container, SWT.NONE);
    defaultValueLabel.setText(OutlineMessages.AddClassVariableDialog_ValueLabel);
    defaultValueText = new Text(container, SWT.BORDER);
    GridData gridData = new GridData();
    gridData.horizontalSpan = 2;
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    defaultValueText.setLayoutData(gridData);
    defaultValueText.addModifyListener(e -> getButton(OK).setEnabled(isOkEnabled()));
  }

  @Override
  protected boolean isOkEnabled() {
    return super.isOkEnabled() && !accessCombo.getText().isEmpty();
  }

  @Override
  protected void saveInput() {
    super.saveInput();
    access = AccessModifyer.valueOf(accessCombo.getText().toUpperCase());
    defaultValue = defaultValueText.getText();
  }
}
