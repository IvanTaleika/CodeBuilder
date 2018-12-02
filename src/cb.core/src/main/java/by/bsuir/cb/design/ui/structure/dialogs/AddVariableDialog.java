package by.bsuir.cb.design.ui.structure.dialogs;

import by.bsuir.cb.design.ui.GridLayoutFactory;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
  private Text nameText;
  private String returnType;
  private String name;
  private Composite container;
  private Text typeText;

  @Override
  public int open() {

    return super.open();
  }

  public AddVariableDialog(Shell parentShell) {
    super(parentShell);
  }

  @Override
  public void create() {
    super.create();
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    setTitle(DialogsMessages.AddVariableDialog_Title);
    setMessage(DialogsMessages.AddMethodDialog_Message);
    Composite area = (Composite) super.createDialogArea(parent);
    container = new Composite(area, SWT.NONE);
    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    GridLayout gridLayout = GridLayoutFactory.create(3, false);
    container.setLayout(gridLayout);

    createReturnTypePart(container);

    createNamePart(container);
    nameText.addModifyListener(new ModifyListener() {
      @Override
      public void modifyText(ModifyEvent e) {
        verifyInput();
      }
    });
    return area;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return returnType;
  }

  private void createReturnTypePart(Composite container) {
    Label typeLabel = new Label(container, SWT.NONE);
    typeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    typeLabel.setText(DialogsMessages.AddVariableDialog_ReturnTypeLabel);
  }

  private void createNamePart(Composite container) {

    typeText = new Text(container, SWT.BORDER);
    typeText.setEditable(false);
    typeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Button chooseClassButton = new Button(container, SWT.NONE);
    chooseClassButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        DialogUtils.selectJavaType(AddVariableDialog.this.getShell())
            .ifPresent(t -> typeText.setText(t.getFullyQualifiedName().replace('$', '.')));
      }
    });
    chooseClassButton.setText(DialogsMessages.TypeChooseButton_Text);
    Label accessLabel = new Label(container, SWT.NONE);
    accessLabel.setText(DialogsMessages.AddVariableDialog_NameLabel);
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

  private void saveInput() {
    returnType = typeText.getText();
    name = nameText.getText();
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(DialogsMessages.AddVariableDialog_ShellTitle);
  }

  @Override
  protected void okPressed() {
    saveInput();
    super.okPressed();
  }

  private void verifyInput() {
    if (!typeText.getText().isEmpty() && !nameText.getText().isEmpty()) {
      getButton(OK).setEnabled(true);
    } else {
      getButton(OK).setEnabled(false);
    }
  }

  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    super.createButtonsForButtonBar(parent);
    getButton(OK).setEnabled(false);
  }
}
