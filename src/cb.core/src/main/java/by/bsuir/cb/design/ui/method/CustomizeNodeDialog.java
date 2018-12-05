package by.bsuir.cb.design.ui.method;

import by.bsuir.cb.CodeBuilder;
import by.bsuir.cb.design.code.IGenerative;
import by.bsuir.cb.design.code.Keyword;
import by.bsuir.cb.design.ui.utils.GridLayoutFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
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
import org.eclipse.swt.widgets.Text;

public class CustomizeNodeDialog extends TitleAreaDialog {
  private static final ILog LOGGER = CodeBuilder.getDefault().getLog();
  @Getter
  @Setter
  private Map<String, IGenerative> keywordsMap;
  @Getter
  @Setter
  private Map<String, String> variableTypeNameMap;
  private Map<String, Combo> keywordComboMap;
  private Text text;

  public CustomizeNodeDialog(Shell parentShell) {
    super(parentShell);
    variableTypeNameMap = new HashMap<>();
  }

  @Override
  public void create() {
    super.create();
    setTitle(TreeViewMessages.CustomizeNodeDialog_Title);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    keywordComboMap = new HashMap<>();
    Composite area = (Composite) super.createDialogArea(parent);
    Composite container = new Composite(area, SWT.NONE);
    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    GridLayout layout = GridLayoutFactory.create(3, false);
    layout.numColumns = 3;
    container.setLayout(layout);

    keywordsMap.forEach((w, k) -> createInputWidgets(w, k, container));

    return area;
  }

  private void createInputWidgets(String word, IGenerative key, Composite container) {
    if (key instanceof Keyword) {
      Label lblNewLabel = new Label(container, SWT.NONE);
      lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
      lblNewLabel.setText(word);

      Combo combo = new Combo(container, SWT.NONE);
      combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
      combo.setItems(variableTypeNameMap.keySet().toArray(new String[0]));
      combo.addModifyListener(e -> {
        var type = variableTypeNameMap.get(combo.getText());
        if (type != null && !type.matches(((Keyword) key).getType())) {
          setMessage(
              TreeViewMessages.CustomizeNodeDialog_TypeMismatchMessage + " \"" + word + "\"",
              IMessageProvider.WARNING);
        }
      });
      keywordComboMap.put(word, combo);
      text = new Text(container, SWT.BORDER);
      text.setEditable(false);
      text.setText(((Keyword) key).getType());
      text.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
    }
  }

  @Override
  protected boolean isResizable() {
    return true;
  }

  private void saveInput() {
    for (Entry<String, Combo> entry : keywordComboMap.entrySet()) {
      if (!entry.getValue().getText().isEmpty()) {
        var generative = keywordsMap.get(entry.getKey());
        if (generative instanceof Keyword) {
          ((Keyword) generative).setValue(entry.getValue().getText());
        }
      }
    }
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(TreeViewMessages.CustomizeNodeDialog_ShellTitle);
  }


  @Override
  protected void okPressed() {
    saveInput();
    super.okPressed();
  }

  public void setVariables(List<IField> variables) {
    variables.forEach(v -> {
      try {
        variableTypeNameMap
            .put(v.getElementName(), Signature.getSignatureQualifier(v.getTypeSignature()) + '.'
                + Signature.getSignatureSimpleName(v.getTypeSignature()));
      } catch (JavaModelException e) {
        LOGGER.log(new Status(Status.ERROR, CodeBuilder.PLUGIN_ID, e.getMessage(), e));
      }
    });
  }
}
