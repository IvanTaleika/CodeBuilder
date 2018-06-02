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
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import cb.core.exceptions.CBResourceException;
import cb.core.ui.design.IDesignViewPart;
import cb.core.ui.design.operations.components.IOperationListener;
import cb.core.ui.design.operations.components.Operation;
import cb.core.ui.design.operations.components.factories.ExpandItemFactory;
import cb.core.ui.design.operations.components.factories.OperationWidgetFactory;
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
    try {

      String errors = buildExpandBar(operationsExpandBar, root);
    } catch (CBResourceException e) {
      // TODO: OutputDialog with errors
    }


    scrolledComposite.setContent(operationsExpandBar);
    scrolledComposite.setMinSize(operationsExpandBar.computeSize(SWT.DEFAULT, SWT.DEFAULT));
  }

  private String buildExpandBar(ExpandBar expandBar, Node root)
      throws CBResourceException {
    // TODO add check on empty tag attr
    String errorMessage = "";
    try {
      NodeList categories = root.getChildNodes();
      for (int i = 0; i < categories.getLength(); i++) {
        Node category = categories.item(i);
        if (category.getNodeType() == Node.ELEMENT_NODE) {
          String categoryErrorMessage = "";
          String categoryName = null;
          NamedNodeMap categoryAttributes = category.getAttributes();
          categoryName = categoryAttributes.getNamedItem(categoryNameAttribute).getNodeValue();
          String categoryIsExpanded =
              categoryAttributes.getNamedItem(categoryIsExpandedAttribute).getNodeValue();
          ExpandItem expandItem =
              ExpandItemFactory.create(expandBar, categoryName, categoryIsExpanded.equals("true"));

          Composite composite = new Composite(expandBar, SWT.NONE);
          // TODO change composite background
          composite.setLayout(GridLayoutFactory.create(columsNumber, false, 1, 1));

          expandItem.setControl(composite);

          categoryErrorMessage += buildExpandItemComposite(composite, category);

          expandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

          if (!categoryErrorMessage.isEmpty()) {
            categoryErrorMessage = "Category " + categoryName + " has errors:" + errorMessage;
            errorMessage += categoryErrorMessage;
          }
        }
      }
    } catch (Exception e) {
      throw new CBResourceException("Unable to parse category's data:\n " + e.getMessage(), e);
    }
    return errorMessage;
  }

  private String buildExpandItemComposite(Composite composite, Node category) {
    String errorMessage = "";
    try {
      NodeList categoryOperations = category.getChildNodes();
      for (int j = 0; j < categoryOperations.getLength(); j++) {
        Node operation = categoryOperations.item(j);
        if (operation.getNodeType() == Node.ELEMENT_NODE) {
          try {
            operations.add(OperationWidgetFactory.create(composite, (Element) operation));
          } catch (CBResourceException cbResourceException) {
            errorMessage += cbResourceException.getMessage() + "\n";
          }

        }
      }

    } catch (Exception e) {
      return "Unable to parse operation's data:\n " + e.getMessage();
    }
    return errorMessage;
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
