package cb.core.ui.design.operations.components.factories;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

//TODO make sure that we cannot implements any Interface
public class ExpandItemFactory {


  //TODO add xml Node as an argument
  public static ExpandItem create(ExpandBar parent) {
    ExpandItem item = new ExpandItem(parent, SWT.NONE);
    //FIXME text from xml
    item.setText("Basic");
    item.setExpanded(true);;
    return item;
  }

}
