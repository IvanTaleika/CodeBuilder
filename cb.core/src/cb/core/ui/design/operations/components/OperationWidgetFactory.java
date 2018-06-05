package cb.core.ui.design.operations.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.w3c.dom.Element;
import cb.core.editors.designEditor.node.NodeFactory;
import cb.core.exceptions.CBResourceException;
import cb.core.ui.utils.BundleResourceProvider;
import cb.core.utils.XMLParseUtils;

public class OperationWidgetFactory {
  private static final String operationTextAttribute = "text";
  private static final String operationToolTipAttribute = "toolTip";
  private static final String operationImageAttribute = "image";


  public static IOperationWidget create(Composite parent, Element widgetData)
      throws CBResourceException {
    // TODO add different Widgets
    OperationButton operationButton = new OperationButton();


    String text = widgetData.getAttribute(operationTextAttribute);
    String toolTip = widgetData.getAttribute(operationToolTipAttribute);
    String imageClassPath = widgetData.getAttribute(operationImageAttribute);

    try {
      operationButton.setNode(
          NodeFactory.create(XMLParseUtils.getElements(widgetData.getChildNodes()).getFirst()));
    } catch (Exception e) {
      throw new CBResourceException(e.getMessage() + " exeption in operation " + text, e);
    }

    Button button = operationButton.buildUI(parent, SWT.FLAT | SWT.TOGGLE);

    button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    button.setToolTipText(toolTip);
    
    
    operationButton.setIcon(BundleResourceProvider.getImage(imageClassPath));
    operationButton.setName(text);
    return operationButton;
  }

}
