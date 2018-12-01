package by.bsuir.cb.design.ui.structure.dialogs;

import by.bsuir.cb.design.ui.GridLayoutFactory;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddMethodDialog extends TitleAreaDialog {
  private String[] accesses = {"public", "protected", "private"};
  private String[] returnTypes =
      {"byte", "short", "int", "long", "float", "double", "boolean", "char", "String", "Object"};
  private Combo accessCombo;
  private Combo returnTypeCombo;
  private Text nameText;
  private Text variablesText;

  private String access;
  private String returnType;
  private String name;
  private String variables;


  public AddMethodDialog(Shell parentShell) {
    super(parentShell);
  }

  @Override
  public void create() {
    super.create();
    setTitle(DialogsMessages.AddMethodDialog_Title);
    //TODO Message disappear after closing dialog
    setMessage(DialogsMessages.AddMethodDialog_Message, IMessageProvider.INFORMATION);
  }

  @Override
  protected Control createDialogArea(Composite parent) {

    Composite area = (Composite) super.createDialogArea(parent);
    Composite container = new Composite(area, SWT.NONE);
    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    GridLayout layout = GridLayoutFactory.create(2, false);
    container.setLayout(layout);

    createAccessPart(container);
    createReturnTypePart(container);

    createNamePart(container);
    createVariablesPart(container);

    accessCombo.addModifyListener(new ModifyListener() {
      @Override
      public void modifyText(ModifyEvent e) {
        verifyInput();
      }
    });
    returnTypeCombo.addModifyListener(new ModifyListener() {
      @Override
      public void modifyText(ModifyEvent e) {
        verifyInput();
      }
    });
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

  public String getAccess() {
    return access;
  }

  public String getReturnType() {
    return returnType;
  }

  public String getVariables() {
    return variables;
  }

  private void createAccessPart(Composite container) {
    Label accessLabel = new Label(container, SWT.NONE);
    accessLabel.setText(DialogsMessages.AddMethodDialog_AccessLabel);

    accessCombo = new Combo(container, SWT.READ_ONLY);
    accessCombo.setItems(accesses);

    GridData gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    accessCombo.setLayoutData(gridData);
  }

  private void createReturnTypePart(Composite container) {
    Label accessLabel = new Label(container, SWT.NONE);
    accessLabel.setText(DialogsMessages.AddMethodDialog_ReturnTypeLabel);

    returnTypeCombo = new Combo(container, SWT.NONE);
    returnTypeCombo.setItems(returnTypes);

    GridData gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    returnTypeCombo.setLayoutData(gridData);
  }

  private void createNamePart(Composite container) {
    Label accessLabel = new Label(container, SWT.NONE);
    accessLabel.setText(DialogsMessages.AddMethodDialog_NameLabel);
    // TODO add H_scroll?
    nameText = new Text(container, SWT.BORDER);

    GridData gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    nameText.setLayoutData(gridData);
  }

  private void createVariablesPart(Composite container) {
    Label accessLabel = new Label(container, SWT.NONE);
    accessLabel.setText(DialogsMessages.AddMethodDialog_MathodValuesLabel);
    // TODO add H_scroll?
    variablesText = new Text(container, SWT.BORDER);

    GridData gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    variablesText.setLayoutData(gridData);
  }

  @Override
  protected boolean isResizable() {
    return true;
  }

  private void saveInput() {
    access = accessCombo.getText();
    returnType = returnTypeCombo.getText();
    name = nameText.getText();
    variables = variablesText.getText();
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(DialogsMessages.AddMethodDialog_ShellTitle);
  }


  @Override
  protected void okPressed() {
    saveInput();
    super.okPressed();
  }

  private void verifyInput() {
    if (!accessCombo.getText().isEmpty() && !returnTypeCombo.getText().isEmpty()
        && !nameText.getText().isEmpty()) {
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
