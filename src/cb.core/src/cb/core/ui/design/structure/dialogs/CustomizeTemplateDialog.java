package cb.core.ui.design.structure.dialogs;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import cb.core.ui.utils.GridLayoutFactory;

public class CustomizeTemplateDialog extends TitleAreaDialog {
  private Map<String, String> methodVariables;
  private Map<String, String> nodeKeywordValueMap;
  private List<Combo> variablesCombo;

//  private List<String> 


  public CustomizeTemplateDialog(Shell parentShell) {
    super(parentShell);
  }

  @Override
  public void create() {
    super.create();
    setTitle(DialogsMessages.CustomizeNodeDialog_Title);
    setMessage(DialogsMessages.CustomizeNodeDialog_Message, IMessageProvider.INFORMATION);
    }

  @Override
  protected Control createDialogArea(Composite parent) {
    variablesCombo = new LinkedList<Combo>();
    Composite area = (Composite) super.createDialogArea(parent);
    Composite container = new Composite(area, SWT.NONE);
    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    GridLayout layout = GridLayoutFactory.create(2, false);
    container.setLayout(layout);
    String[] variables = new String[methodVariables.size()];
    int i = 0;
    for (Map.Entry<String, String> variable : methodVariables.entrySet()) {
      variables[i] = variable.getKey();
      i++;
    }
    for (Map.Entry<String, String> keywordValue : nodeKeywordValueMap.entrySet()) {
      Label messageLabel = new Label(container, SWT.NONE);
      messageLabel.setText(DialogsMessages.CustomizeNodeDialog_LabelDefaultMessage + keywordValue.getKey());
      Combo combo = new Combo(container,  SWT.NONE);
      
      GridData gridData = new GridData();
      gridData.grabExcessHorizontalSpace = true;
      gridData.horizontalAlignment = GridData.FILL;
      combo.setLayoutData(gridData);
      
      combo.setItems(variables);
      combo.add(keywordValue.getValue(), 0);
      combo.select(0);
      variablesCombo.add(combo);
     
    }

    return area;
  }


  @Override
  protected boolean isResizable() {
    return true;
  }

  private void saveInput() {
    int i = 0;
    for (Map.Entry<String, String> keywordValue : nodeKeywordValueMap.entrySet()) {
      keywordValue.setValue(variablesCombo.get(i).getText());
      i++;
    }
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(DialogsMessages.CustomizeNodeDialog_ShellTitle);
  }


  @Override
  protected void okPressed() {
    saveInput();
    super.okPressed();
  }



  public Map<String, String> getMethodVariables() {
    return methodVariables;
  }

  public void setMethodVariables(Map<String, String> methodVariables) {
    this.methodVariables = methodVariables;
  }

  public Map<String, String> getNodeKeywordValueMap() {
    return nodeKeywordValueMap;
  }

  public void setNodeKeywordValueMap(Map<String, String> nodeKeywordsValues) {
    this.nodeKeywordValueMap = nodeKeywordsValues;
  }
}
