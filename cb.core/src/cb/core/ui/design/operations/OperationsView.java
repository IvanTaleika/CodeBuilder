package cb.core.ui.design.operations;

import java.io.File;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import cb.core.exceptions.CBResourceException;
import cb.core.ui.design.IDesignViewPart;
import cb.core.ui.design.operations.components.IOperationListener;
import cb.core.ui.design.operations.components.Operation;
import cb.core.ui.design.operations.components.factories.ExpandItemFactory;
import cb.core.ui.design.operations.components.factories.OperationToggleButtonFactory;
import cb.core.ui.utils.GridLayoutFactory;

public class OperationsView implements IDesignViewPart {
  private int columsNumber;
  private Composite uiParent;
  private Group operationsGroup;
  private LinkedList<Operation> operations;
  private File operationsTemplate;

  // TODO move declarations somewhere
  private final String categoryNameAttribute = "name";
  private final String categoryIsExpandedAttribute = "expanded";
  private final String operationTextAttribute = "text";
  private final String operationToolTipAttribute = "toolTip";
  private final String operationImageAttribute = "image";

  public OperationsView(Composite parent, File operationsTemplate) {
    operations = new LinkedList<>();
    uiParent = parent;
    this.operationsTemplate = operationsTemplate;
    // TODO move it to plugin preferences
    columsNumber = 2;
  }


  public void addOperationsListener(IOperationListener listener) {
    for (Operation operation : operations) {
      operation.addListener(listener);
    }
  }

  public void removeOperationListener(IOperationListener listener) {
    for (Operation operation : operations) {
      operation.removeListener(listener);
    }
  }

  @Override
  public Group getGUI() {
    return operationsGroup;
  }

  @Override
  public void buildGUI() throws CBResourceException {
    Document document;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      document = builder.parse(operationsTemplate);
    } catch (Exception e) {
      throw new CBResourceException("Unable to open operations template file.");
    }

    operationsGroup = new Group(uiParent, SWT.NONE);
    operationsGroup.setText(OperationsControllerMessages.OperationsController_title);
    operationsGroup.setLayout(GridLayoutFactory.create(1, false, 0, 0));

    ScrolledComposite scrolledComposite = new ScrolledComposite(operationsGroup, SWT.V_SCROLL);
    scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    scrolledComposite.setExpandHorizontal(true);
    scrolledComposite.setExpandVertical(true);

    ExpandBar operationsExpandBar = new ExpandBar(scrolledComposite, SWT.NONE);

    Node root = document.getDocumentElement();
    NodeList categories = root.getChildNodes();
    buildExpandBar(operationsExpandBar, categories);


    scrolledComposite.setContent(operationsExpandBar);
    scrolledComposite.setMinSize(operationsExpandBar.computeSize(SWT.DEFAULT, SWT.DEFAULT));
  }

  private void buildExpandBar(ExpandBar expandBar, NodeList categories) throws CBResourceException {
    // TODO add check on empty tag attr
    try {
      for (int i = 0; i < categories.getLength(); i++) {
        Node category = categories.item(i);
        if (category.getNodeType() == Node.ELEMENT_NODE) {
          NamedNodeMap categoryAttributes = category.getAttributes();
          String categoryName =
              categoryAttributes.getNamedItem(categoryNameAttribute).getNodeValue();
          String categoryIsExpanded =
              categoryAttributes.getNamedItem(categoryIsExpandedAttribute).getNodeValue();
          ExpandItem expandItem =
              ExpandItemFactory.create(expandBar, categoryName, categoryIsExpanded.equals("true"));

          Composite composite = new Composite(expandBar, SWT.NONE);
          // TODO change composite background
          composite.setLayout(GridLayoutFactory.create(columsNumber, false, 1, 1));

          expandItem.setControl(composite);
          NodeList itemOperations = category.getChildNodes();
          buildExpandItemComposite(composite, itemOperations);

          expandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
        }
      }
    } catch (Exception e) {
      throw new CBResourceException("Unable to parse operation template file. " + e.getMessage(),
          e);
    }
  }

  private void buildExpandItemComposite(Composite composite, NodeList itemOperations) {
    for (int j = 0; j < itemOperations.getLength(); j++) {
      Node operation = itemOperations.item(j);
      if (operation.getNodeType() == Node.ELEMENT_NODE) {
        NamedNodeMap operationAttributes = operation.getAttributes();
        String operationText =
            operationAttributes.getNamedItem(operationTextAttribute).getNodeValue();
        String operationToolTip =
            operationAttributes.getNamedItem(operationToolTipAttribute).getNodeValue();
        String operationImage =
            operationAttributes.getNamedItem(operationImageAttribute).getNodeValue();
        // TODO get NODE value
        operations.add(OperationToggleButtonFactory.create(composite, operationText,
            operationToolTip, operationImage));
      }
    }
  }

  @Override
  public void setParent(Composite parent) {
    uiParent = parent;

  }


  @Override
  public Composite getParent() {
    return uiParent;

  }


  public int getColumsNumber() {
    return columsNumber;
  }


  public void setColumsNumber(int columsNumber) {
    this.columsNumber = columsNumber;
  }
}
