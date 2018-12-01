package by.bsuir.cb.design.ui.operation;

import by.bsuir.cb.design.code.node.MethodNode;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

// TODO make abstract class instead
public interface IOperation {

  Widget getUi();

  Widget buildUi(Composite parent);

  boolean isSelected();

  void setSelection(boolean isSelected);

  MethodNode getNode();

  void setNode(MethodNode operationNode);

  void addListener(IOperationListener listener);

  void removeListener(IOperationListener listener);

  String getName();

  void setName(String name);

  String getTooltip();

  void setTooltip(String tooltip);

  public void setIcon(Image icon);

  public Image getIcon();
}
