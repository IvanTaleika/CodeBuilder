package by.bsuir.cb.design.ui.operation;

import by.bsuir.cb.BundleResourceProvider;
import by.bsuir.cb.design.CbResourceException;
import by.bsuir.cb.design.code.node.NodeFactory;
import by.bsuir.cb.utils.XMLParseUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.w3c.dom.Element;

// TODO delete
public class OperationWidgetFactory {
  private static final String operationTextAttribute = "text";
  private static final String operationToolTipAttribute = "toolTip";
  private static final String operationImageAttribute = "image";


  public static IOperation create(Composite parent, Element widgetData)
      throws CbResourceException {
    // TODO add different Widgets
    OperationButton operationButton = new OperationButton();

    String text = widgetData.getAttribute(operationTextAttribute);
    try {
      operationButton.setNode(
          NodeFactory.create(XMLParseUtils.getElements(widgetData.getChildNodes()).getFirst()));
    } catch (Exception e) {
      throw new CbResourceException(e.getMessage() + " exeption in operation " + text, e);
    }

    Button button = operationButton.buildUi(parent);

    button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    String toolTip = widgetData.getAttribute(operationToolTipAttribute);
    button.setToolTipText(toolTip);


    String imageClassPath = widgetData.getAttribute(operationImageAttribute);
    operationButton.setIcon(BundleResourceProvider.getImage(imageClassPath));
    operationButton.setName(text);
    return operationButton;
  }

}
