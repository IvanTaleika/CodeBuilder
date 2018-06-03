package cb.core.ui.design.operations.components;

import java.util.LinkedList;
import org.eclipse.swt.graphics.Image;
import cb.core.editors.designEditor.node.MethodNode;

public abstract class Operation implements IOperation {
  private final LinkedList<IOperationListener> listeners;
  private MethodNode operationNode;
  private Image icon;
  private String name;
  // TODO is it should be here?

  public Operation() {
    listeners = new LinkedList<IOperationListener>();
  }

  public LinkedList<IOperationListener> getListeners() {
    return listeners;

  }

  public void addListener(IOperationListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IOperationListener listener) {
    listeners.remove(listener);
  }

  public MethodNode getOperationNode() {
    return operationNode;
  }

  public void setOperationNode(MethodNode operationNode) {
    this.operationNode = operationNode;
  }

  public String getName() {
    return name;
  }

  public Image getIcon() {
    return icon;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIcon(Image icon) {
    this.icon = icon;
  }
}
