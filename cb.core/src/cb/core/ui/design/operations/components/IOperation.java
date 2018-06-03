// TODO change package
package cb.core.ui.design.operations.components;

import org.eclipse.swt.graphics.Image;
import cb.core.editors.designEditor.node.MethodNode;
public interface IOperation {

  boolean isSelected();

  void setSelection(boolean isSelected);

  public MethodNode getOperationNode();
  
  public void setOperationNode(MethodNode operationNode);
  
  void addListener(IOperationListener listener);

  void removeListener(IOperationListener listener);
  
  public String getName();
  
  public Image getIcon();

  public void setName(String name);

  public void setIcon(Image icon);


}
