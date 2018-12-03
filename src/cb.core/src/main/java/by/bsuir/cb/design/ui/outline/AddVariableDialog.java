package by.bsuir.cb.design.ui.outline;

import by.bsuir.cb.design.ui.utils.DialogUtils;
import by.bsuir.cb.design.ui.utils.GridLayoutFactory;
import lombok.Getter;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddVariableDialog extends TitleAreaDialog {
  @Getter
  private IType type;
  @Getter
  private String name;
  @Getter
  private Composite container;
  @Getter
  private GridLayout gridLayout;
  private Text nameText;
  private Text typeText;

  public AddVariableDialog(Shell parentShell) {
    super(parentShell);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    setTitle(OutlineMessages.AddVariableDialog_Title);
    setMessage(OutlineMessages.AddVariableDialog_Message);
    Composite area = (Composite) super.createDialogArea(parent);
    container = new Composite(area, SWT.NONE);
    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    gridLayout = GridLayoutFactory.create(3, false);
    container.setLayout(gridLayout);
    createReturnTypePart(container);
    createNamePart(container);
    nameText.addModifyListener(e -> getButton(OK).setEnabled(isOkEnabled()));
    typeText.addModifyListener(e -> getButton(OK).setEnabled(isOkEnabled()));
    return area;
  }

  private void createReturnTypePart(Composite container) {
    Label typeLabel = new Label(container, SWT.NONE);
    typeLabel.setText(OutlineMessages.AddVariableDialog_ReturnTypeLabel);
  }

  private void createNamePart(Composite container) {
    typeText = new Text(container, SWT.BORDER);
    typeText.setEditable(false);
    typeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Button chooseClassButton = new Button(container, SWT.NONE);
    chooseClassButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        DialogUtils.openJavaTypeDialog(AddVariableDialog.this.getShell())
            .ifPresent(t -> {
              typeText.setText(t.getFullyQualifiedName('.'));
              type = t;
            });
      }
    });
    chooseClassButton.setText(OutlineMessages.TypeChooseButton_Text);
    Label accessLabel = new Label(container, SWT.NONE);
    accessLabel.setText(OutlineMessages.AddVariableDialog_NameLabel);
    nameText = new Text(container, SWT.BORDER);

    GridData gridData = new GridData();
    gridData.horizontalSpan = 2;
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    nameText.setLayoutData(gridData);
  }

  @Override
  protected boolean isResizable() {
    return true;
  }

  protected void saveInput() {
    name = nameText.getText();
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(OutlineMessages.AddVariableDialog_ShellTitle);
  }

  @Override
  protected void okPressed() {
    saveInput();
    super.okPressed();
  }

  protected boolean isOkEnabled() {
    return !typeText.getText().isEmpty() && !nameText.getText().isEmpty();
  }

  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    super.createButtonsForButtonBar(parent);
    getButton(OK).setEnabled(false);
  }

}
