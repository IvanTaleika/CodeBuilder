package cb.core.ui.design.operations.components.factories;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import cb.core.ui.design.operations.components.OperationButton;
import cb.core.ui.design.operations.components.OperationWidget;
import cb.core.ui.utils.BundleResourceProvider;

//TODO add interface for OperationFactory
public class OperationButtonFactory implements IOperationWidgetFactory {

  //TODO add xml Node as an argument
  public static OperationWidget create(Composite parent) {
    OperationButton operationButton = new OperationButton(parent, SWT.FLAT | SWT.TOGGLE);
    Button button = (Button) operationButton.getUI();
    //TODO check GridData
    button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    
    //TODO text from xml
    button.setText("My button");
    //TODO path frim xml
    button.setImage(BundleResourceProvider.getImage("images/operations/icons/begin_end.gif"));

    

    //TODO convert to image
    return operationButton;
  }

}
