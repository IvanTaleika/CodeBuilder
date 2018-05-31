package cb.core.ui.design.operations.components.factories;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import cb.core.ui.design.operations.components.OperationButton;
import cb.core.ui.design.operations.components.OperationWidget;
import cb.core.ui.utils.BundleResourceProvider;

// TODO add interface for OperationFactory
public class OperationToggleButtonFactory implements IOperationWidgetFactory {

  //TODO customize button look
  public static OperationWidget create(Composite parent, String text, String toolTip,
      String imageClassPath) {
    OperationButton operationButton = new OperationButton(parent, SWT.FLAT | SWT.TOGGLE);
    Button button = (Button) operationButton.getUI();
    // TODO check GridData
    button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    button.setText(text);
    button.setToolTipText(toolTip);
    button.setImage(BundleResourceProvider.getImage(imageClassPath));

    return operationButton;
  }

}
