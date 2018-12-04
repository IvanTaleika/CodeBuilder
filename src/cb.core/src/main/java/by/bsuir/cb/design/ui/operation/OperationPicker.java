package by.bsuir.cb.design.ui.operation;

import by.bsuir.cb.BundleResourceProvider;
import by.bsuir.cb.design.ui.operation.xml.OperationXmlBuilder;
import by.bsuir.cb.design.ui.operation.xml.XmlParsingException;
import by.bsuir.cb.design.ui.utils.GridLayoutFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;

public class OperationPicker {
  private List<IOperationListener> listeners;
  private Group operationsGroup;
  private Map<Category, ExpandItem> categoryItemMap;
  private ExpandBar operationsExpandBar;
  private Composite parent;
  private Button selectedButton;

  /**
   * Instantiates a new operation picker.
   */
  public OperationPicker(Composite parent) {
    listeners = new LinkedList<>();
    categoryItemMap = new HashMap<>();
    this.parent = parent;
    buildUi();
  }

  public Group getUi() {
    return operationsGroup;
  }

  private void buildUi() {
    operationsGroup = new Group(parent, SWT.NONE);
    operationsGroup.setText(OperationsPickerMessages.OperationsPicker_title);
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

  public void addOperationListener(IOperationListener listener) {
    listeners.add(listener);
  }

  public void removeOperationListener(IOperationListener listener) {
    listeners.remove(listener);
  }

  public void notifyOperationListeners(Operation operation) {
    listeners.forEach(l -> l.operationSelectionChanged(operation));
  }

  /**
   * Loads {@link Operation}s from source XML file.
   * 
   * @param xml source XML file.
   * @throws XmlParsingException if xml is not valid or well-formed.
   */
  public void loadOperations(InputStream xml) throws XmlParsingException {
    var builder = new OperationXmlBuilder();
    builder.buildCategories(xml);
    for (Category category : builder.getCategories()) {
      var expandItem = categoryItemMap.computeIfAbsent(category, this::createExpandItem);
      category.getOperations().forEach(o -> addOperationButton(expandItem, o));
      expandItem.setHeight(expandItem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
    }
  }

  private ExpandItem createExpandItem(Category category) {
    var expandItem = new ExpandItem(operationsExpandBar, SWT.NONE);
    expandItem.setText(category.getTitle());
    expandItem.setExpanded(true);
    var expandItemComposit = new Composite(operationsExpandBar, SWT.NONE);
    expandItemComposit.setLayout(GridLayoutFactory.create(2, true, 1, 1));
    expandItem.setControl(expandItemComposit);
    return expandItem;
  }

  private void addOperationButton(ExpandItem expandItem, Operation operation) {
    var button = new Button((Composite) expandItem.getControl(), SWT.FLAT | SWT.TOGGLE);
    button.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (button.getSelection()) {
          notifyOperationListeners(operation);
          if (selectedButton != null) {
            selectedButton.setSelection(false);
          }
          selectedButton = button;
        } else {
          selectedButton = null;
          notifyOperationListeners(null);
        }
      }
    });
    button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    button.setToolTipText(operation.getDescription());
    button.setImage(BundleResourceProvider.getImage(operation.getIconPath()));
    button.setText(operation.getName());
  }

}
