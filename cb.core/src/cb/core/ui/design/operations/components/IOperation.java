// TODO change package
package cb.core.ui.design.operations.components;

import org.eclipse.swt.graphics.Image;
import cb.core.editors.designEditor.node.MethodNode;
//TODO move node from Operation
public interface IOperation {

  boolean isSelected();

  void setSelection(boolean isSelected);

  MethodNode getNode();

  void setNode(MethodNode operationNode);

  void addListener(IOperationListener listener);

  void removeListener(IOperationListener listener);

  String getName();

  void setName(String name);

  public void setIcon(Image icon);

  public Image getIcon();
}
