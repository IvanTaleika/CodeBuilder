package by.bsuir.cb.design.ui.operation;

import by.bsuir.cb.design.ui.GridLayoutFactory;
import by.bsuir.cb.design.ui.operation.xml.StaxBuilder;
import by.bsuir.cb.design.ui.operation.xml.XmlParsingException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;

/**
 * The Class OperationPicker.
 */
public class OperationPicker {
  private Group operationsGroup;
  private List<Category> categories;
  private ExpandBar operationsExpandBar;

  /**
   * Instantiates a new operation picker.
   *
   */
  public OperationPicker() {
    categories = new LinkedList<>();
  }

  public Group getUi() {
    return operationsGroup;
  }

  public void buildUi(Composite parent) {
    operationsGroup = new Group(parent, SWT.NONE);
    operationsGroup.setText(OperationsPickerMessages.OperationsPicker_title);
    // FIXME poor code
    operationsGroup.setLayout(GridLayoutFactory.create(1, false, 0, 0));

    ScrolledComposite scrolledComposite = new ScrolledComposite(operationsGroup, SWT.V_SCROLL);
    scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    scrolledComposite.setExpandHorizontal(true);
    scrolledComposite.setExpandVertical(true);

    operationsExpandBar = new ExpandBar(scrolledComposite, SWT.NONE);
    operationsExpandBar.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

    scrolledComposite.setContent(operationsExpandBar);
    scrolledComposite.setMinSize(operationsExpandBar.computeSize(SWT.DEFAULT, SWT.DEFAULT));
  }

  /**
   * Loads {@link IOperation}s from source XML file.
   * 
   * @param xml source XML file.
   * @throws XmlParsingException if xml is not valid or well-formed.
   */
  public void loadOperations(InputStream xml) throws XmlParsingException {
    // TODO read about DI in Eclipse
    var builder = new StaxBuilder();
    builder.buildCategories(xml);
    for (Category category : builder.getCategories()) {
      var i = categories.indexOf(category);
      if (i != -1) {
        categories.get(i).addOperations(category.getOperations());
      } else {
        categories.add(category);
        category.buildUi(operationsExpandBar);
      }
    }
  }

  public List<IOperation> getOperations() {
    List<IOperation> operations = new LinkedList<>();
    categories.forEach(c -> operations.addAll(c.getOperations()));
    return operations;
  }

}
