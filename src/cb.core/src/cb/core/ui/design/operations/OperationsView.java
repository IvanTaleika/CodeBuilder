package cb.core.ui.design.operations;

import java.io.File;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import cb.core.exceptions.CBResourceException;
import cb.core.ui.design.operations.components.IOperation;
import cb.core.ui.design.operations.components.IOperationListener;
import cb.core.ui.design.operations.components.OperationWidgetFactory;
import cb.core.ui.design.operations.components.factories.ExpandItemFactory;
import cb.core.ui.utils.GridLayoutFactory;
import cb.core.utils.XMLParseUtils;

public class OperationsView {
  private int columsNumber;
  private Composite parent;
  private Group operationsGroup;
  private LinkedList<IOperation> iOperations;
  private File operationsTemplate;

  private final String categoryNameAttribute = "name";
  private final String categoryIsExpandedAttribute = "expanded";


  public OperationsView(Composite parent, File operationsTemplate) {
    iOperations = new LinkedList<>();
    this.parent = parent;
    this.operationsTemplate = operationsTemplate;
    // TODO move it to plugin preferences
    columsNumber = 2;
  }


  public void addOperationsListener(IOperationListener listener) {
    for (IOperation iOperation : iOperations) {
      iOperation.addListener(listener);
    }
  }

  public void removeOperationListener(IOperationListener listener) {
    for (IOperation iOperation : iOperations) {
      iOperation.removeListener(listener);
    }
  }

  public Group getGUI() {
    return operationsGroup;
  }

  public void buildGUI() throws CBResourceException {
    Document document;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      document = builder.parse(operationsTemplate);
    } catch (Exception e) {
      throw new CBResourceException("Unable to open Operations template file.", e);
    }

    operationsGroup = new Group(parent, SWT.NONE);
    operationsGroup.setText(OperationsControllerMessages.OperationsController_title);
    operationsGroup.setLayout(GridLayoutFactory.create(1, false, 0, 0));

    ScrolledComposite scrolledComposite = new ScrolledComposite(operationsGroup, SWT.V_SCROLL);
    scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    scrolledComposite.setExpandHorizontal(true);
    scrolledComposite.setExpandVertical(true);

    ExpandBar operationsExpandBar = new ExpandBar(scrolledComposite, SWT.NONE);
    operationsExpandBar.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

    Node root = document.getDocumentElement();
    String errors = null;

    errors = buildExpandBar(operationsExpandBar, root);

    if (!errors.isEmpty()) {
      //FIXME turn on after debug
//      MessageDialog.openWarning(parent.getShell(), "CodeBuilder error",
//          "Error with source data, some operations can be inaccessible:\n" + errors);
    }


    scrolledComposite.setContent(operationsExpandBar);
    scrolledComposite.setMinSize(operationsExpandBar.computeSize(SWT.DEFAULT, SWT.DEFAULT));
  }



  private String buildExpandBar(ExpandBar expandBar, Node root) throws CBResourceException {
    String errorMessage = "";
    LinkedList<Element> categories = XMLParseUtils.getElements(root.getChildNodes());
    try {
      for (Element category : categories) {
        String categoryErrorMessage = "";
        String categoryName = category.getAttribute(categoryNameAttribute);
        String categoryIsExpanded = category.getAttribute(categoryIsExpandedAttribute);
        ExpandItem expandItem =
            ExpandItemFactory.create(expandBar, categoryName, categoryIsExpanded.equals("true"));

        Composite composite = new Composite(expandBar, SWT.NONE);
        composite.setLayout(GridLayoutFactory.create(columsNumber, true, 1, 1));

        expandItem.setControl(composite);

        categoryErrorMessage += buildExpandItemComposite(composite, category);

        expandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

        if (!categoryErrorMessage.isEmpty()) {
          errorMessage += "Category " + categoryName + " has errors: " + categoryErrorMessage;
        }
      }
    } catch (Exception e) {
      throw new CBResourceException("Unable to parse category's data:\n " + e.getMessage(), e);
    }
    return errorMessage;
  }

  private String buildExpandItemComposite(Composite composite, Node category) {
    String errorMessage = "";
    LinkedList<Element> operations = XMLParseUtils.getElements(category.getChildNodes());
    try {
      for (Element operation : operations) {
        try {
          iOperations.add(OperationWidgetFactory.create(composite, (Element) operation));
        } catch (CBResourceException cbResourceException) {
          errorMessage += cbResourceException.getMessage() + "\n";
        }
      }
    } catch (Exception e) {
      return "Unable to parse operation's data:\n " + e.getMessage();
    }
    return errorMessage;
  }

  public void setParent(Composite parent) {
    this.parent = parent;
  }


  public Composite getParent() {
    return parent;

  }


  public int getColumsNumber() {
    return columsNumber;
  }


  public void setColumsNumber(int columsNumber) {
    this.columsNumber = columsNumber;
  }
}
