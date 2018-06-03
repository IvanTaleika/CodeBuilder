package cb.core.ui.design.structure.dialogs;

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
import cb.core.ui.utils.GridLayoutFactory;

//TODO add abstract parent
public class AddValueDialog extends TitleAreaDialog {
  private String[] accesses = {"public", "protected", "private"};
  // TODO is it too many?
  private String[] returnTypes =
      {"byte", "short", "int", "long", "float", "double", "boleam", "char", "String", "Object"};
  private Combo accessCombo;
  private Combo typeCombo;
  private Text nameText;
  // TODO add initialization

  private String access;
  private String returnType;
  private String name;


  public AddValueDialog(Shell parentShell) {
    super(parentShell);
  }

  @Override
  public void create() {
    super.create();
    setTitle(DialogsMessages.AddValueDialog_Title);
    setMessage(DialogsMessages.AddValueDialog_Message, IMessageProvider.INFORMATION);
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

    accessCombo.addModifyListener(new ModifyListener() {
      @Override
      public void modifyText(ModifyEvent e) {
        verifyInput();
      }
    });
    typeCombo.addModifyListener(new ModifyListener() {
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

  public String getType() {
    return returnType;
  }


  private void createAccessPart(Composite container) {
    Label accessLabel = new Label(container, SWT.NONE);
    accessLabel.setText(DialogsMessages.AddValueDialog_AccessLabel);

    accessCombo = new Combo(container, SWT.READ_ONLY);
    accessCombo.setItems(accesses);

    GridData gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    accessCombo.setLayoutData(gridData);
  }

  private void createReturnTypePart(Composite container) {
    Label accessLabel = new Label(container, SWT.NONE);
    accessLabel.setText(DialogsMessages.AddValueDialog_ReturnTypeLabel);

    typeCombo = new Combo(container, SWT.NONE);
    typeCombo.setItems(returnTypes);

    GridData gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    typeCombo.setLayoutData(gridData);
  }

  private void createNamePart(Composite container) {
    Label accessLabel = new Label(container, SWT.NONE);
    accessLabel.setText(DialogsMessages.AddValueDialog_NameLabel);
    // TODO add H_scroll?
    nameText = new Text(container, SWT.BORDER);

    GridData gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    nameText.setLayoutData(gridData);
  }

  @Override
  protected boolean isResizable() {
    return true;
  }

  private void saveInput() {
    access = accessCombo.getText();
    returnType = typeCombo.getText();
    name = nameText.getText();
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(DialogsMessages.AddValueDialog_ShellTitle);
  }


  @Override
  protected void okPressed() {
    saveInput();
    super.okPressed();
  }

  private void verifyInput() {
    if (!accessCombo.getText().isEmpty() && !typeCombo.getText().isEmpty()
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
