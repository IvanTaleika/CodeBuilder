package cb.core.ui.design.operations.components.factories;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

//TODO make sure that we cannot implements any Interface
public class ExpandItemFactory {
  public static ExpandItem create(ExpandBar parent, String text, boolean isExpanded) {
    ExpandItem item = new ExpandItem(parent, SWT.NONE);
    item.setText(text);
    item.setExpanded(isExpanded);;
    return item;
  }

}
