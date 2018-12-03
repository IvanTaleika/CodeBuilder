package by.bsuir.cb.design.ui.operation;

import by.bsuir.cb.design.code.node.MethodNode;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.swt.graphics.Image;

// @Data
public abstract class AbstractOperation implements IOperation {

  private final List<IOperationListener> listeners = new LinkedList<>();
  private MethodNode operationNode;
  private Image icon;
  private String name;
  private String tooltip;


  public List<IOperationListener> getListeners() {
    return listeners;
  }

  @Override
  public void addListener(IOperationListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removeListener(IOperationListener listener) {
    listeners.remove(listener);
  }

  @Override
  public MethodNode getNode() {
    return operationNode;
  }

  @Override
  public void setNode(MethodNode operationNode) {
    this.operationNode = operationNode;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Image getIcon() {
    return icon;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setIcon(Image icon) {
    this.icon = icon;
  }

  @Override
  public String getTooltip() {
    return tooltip;
  }

  @Override
  public void setTooltip(String tooltip) {
    this.tooltip = tooltip;

  }
}
