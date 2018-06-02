package cb.core.ui.design.operations.components.factories;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import cb.core.exceptions.CBResourceException;
import cb.core.ui.design.operations.components.OperationButton;
import cb.core.ui.design.operations.components.OperationWidget;
import cb.core.ui.utils.BundleResourceProvider;

// TODO add different Widgets
public class OperationWidgetFactory {
  private static final String operationTextAttribute = "text";
  private static final String operationToolTipAttribute = "toolTip";
  private static final String operationImageAttribute = "image";


  public static OperationWidget create(Composite parent, Element widgetData)
      throws CBResourceException {

    String text = widgetData.getAttribute(operationTextAttribute);
    String toolTip = widgetData.getAttribute(operationToolTipAttribute);
    String imageClassPath = widgetData.getAttribute(operationImageAttribute);

    NodeList operationNodesList = widgetData.getChildNodes();
    Element nodeElement = null;
    for (int i = 0; i < operationNodesList.getLength(); i++) {
      if (operationNodesList.item(i).getNodeType() == Node.ELEMENT_NODE) {
        nodeElement = (Element) operationNodesList.item(i);
      }
    }
    OperationButton operationButton = null;

    try {

      operationButton = new OperationButton(parent, SWT.FLAT | SWT.TOGGLE, nodeElement);
    } catch (Exception e) {
      throw new CBResourceException(e.getMessage() + " in operation" + text, e);
    }
    Button button = (Button) operationButton.getUI();
    // TODO check GridData
    button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    button.setText(text);
    button.setToolTipText(toolTip);
    button.setImage(BundleResourceProvider.getImage(imageClassPath));

    return operationButton;
  }

}
