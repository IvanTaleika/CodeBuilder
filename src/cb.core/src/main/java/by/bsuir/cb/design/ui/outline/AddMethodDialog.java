package by.bsuir.cb.design.ui.outline;

import by.bsuir.cb.design.code.AccessModifyer;
import by.bsuir.cb.design.ui.GridLayoutFactory;
import by.bsuir.cb.design.ui.utils.DialogUtils;
import java.util.Arrays;
import lombok.Getter;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddMethodDialog extends TitleAreaDialog {
  @Getter
  private String name;
  @Getter
  private AccessModifyer access;
  @Getter
  private IType returnType;
  @Getter
  private String variables;

  private Combo accessCombo;
  private Text nameText;
  private Text variablesText;
  private Composite container;
  private Text returnTypeText;
  private Button chooseTypeButton;

  public AddMethodDialog(Shell parentShell) {
    super(parentShell);
    setHelpAvailable(false);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    setTitle(OutlineMessages.AddMethodDialog_Title);
    setMessage(OutlineMessages.AddMethodDialog_Message);
    Composite area = (Composite) super.createDialogArea(parent);
    container = new Composite(area, SWT.NONE);
    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    GridLayout gridLayout = GridLayoutFactory.create(3, false);
    container.setLayout(gridLayout);
    createAccessPart(container);
    createNamePart(container);
    createVariablesPart(container);
    returnTypeText.addModifyListener(e -> getButton(OK).setEnabled(isOkEnabled()));
    accessCombo.addModifyListener(e -> getButton(OK).setEnabled(isOkEnabled()));
    nameText.addModifyListener(e -> getButton(OK).setEnabled(isOkEnabled()));
    variablesText.addModifyListener(e -> getButton(OK).setEnabled(isOkEnabled()));
    return area;
  }

  private void createAccessPart(Composite container) {
    Label returnTypeLabel = new Label(container, SWT.NONE);
    returnTypeLabel.setText(OutlineMessages.AddMethodDialog_ReturnTypeLabel);

    returnTypeText = new Text(container, SWT.BORDER);
    returnTypeText.setEditable(false);
    returnTypeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    chooseTypeButton = new Button(container, SWT.NONE);
    chooseTypeButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        DialogUtils.openJavaTypeDialog(AddMethodDialog.this.getShell())
            .ifPresent(t -> {
              returnTypeText.setText(t.getFullyQualifiedName('.'));
              returnType = t;
            });
      }
    });
    chooseTypeButton.setText(OutlineMessages.TypeChooseButton_Text);
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
  }


  private void createNamePart(Composite container) {
    Label accessLabel = new Label(container, SWT.NONE);
    accessLabel.setText(OutlineMessages.AddMethodDialog_NameLabel);
    nameText = new Text(container, SWT.BORDER);

    GridData gridData = new GridData();
    gridData.horizontalSpan = 2;
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    nameText.setLayoutData(gridData);
  }

  private void createVariablesPart(Composite container) {
    Label variablesLabel = new Label(container, SWT.NONE);
    variablesLabel.setText(OutlineMessages.AddMethodDialog_MathodVariablesLabel);
    variablesText = new Text(container, SWT.BORDER);

    GridData gridData = new GridData();
    gridData.horizontalSpan = 2;
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    variablesText.setLayoutData(gridData);
  }

  @Override
  protected boolean isResizable() {
    return true;
  }

  private void saveInput() {
    access = AccessModifyer.valueOf(accessCombo.getText().toUpperCase());
    name = nameText.getText();
    variables = variablesText.getText();
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(OutlineMessages.AddMethodDialog_ShellTitle);
  }


  @Override
  protected void okPressed() {
    saveInput();
    super.okPressed();
  }

  protected boolean isOkEnabled() {
    return !accessCombo.getText().isEmpty() && !returnTypeText.getText().isEmpty()
        && !nameText.getText().isEmpty();
  }


  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    super.createButtonsForButtonBar(parent);
    getButton(OK).setEnabled(false);
  }
}
