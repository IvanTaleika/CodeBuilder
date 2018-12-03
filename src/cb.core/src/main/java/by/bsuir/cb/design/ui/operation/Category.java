package by.bsuir.cb.design.ui.operation;

import by.bsuir.cb.design.ui.utils.GridLayoutFactory;
import java.util.LinkedList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

// TODO add javadoc

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
  @Getter
  @Setter
  @Include
  private String title;
  private ExpandItem expandItem;
  private Composite expandItemComposit;
  @Getter
  @Setter
  private List<IOperation> operations = new LinkedList<>();

  public ExpandItem getUi() {
    return expandItem;
  }

  public void buildUi(ExpandBar parent) {
    expandItem = new ExpandItem(parent, SWT.NONE);
    expandItem.setText(title);
    expandItem.setExpanded(true);
    expandItemComposit = new Composite(parent, SWT.NONE);
    // TODO move to constants;
    expandItemComposit.setLayout(GridLayoutFactory.create(2, true, 1, 1));
    operations.forEach(o -> o.buildUi(expandItemComposit));
    expandItem.setControl(expandItemComposit);
    expandItem.setHeight(expandItemComposit.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
  }

  // FIXME if call will be before buildUi -> nullPointerException
  public void addOperations(List<IOperation> operations) {
    this.operations.addAll(operations);
  }


}
