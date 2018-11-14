package by.bsuir.cb.design.ui.operation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

public class ExpandItemFactory {
  public static ExpandItem create(ExpandBar parent, String text, boolean isExpanded) {
    ExpandItem item = new ExpandItem(parent, SWT.NONE);
    item.setText(text);
    item.setExpanded(isExpanded);
    return item;
  }

}
