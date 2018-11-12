package by.bsuir.cb.design.ui.components;

import by.bsuir.cb.design.code.node.MethodNode;

import java.util.LinkedList;

import org.eclipse.swt.graphics.Image;

public abstract class Operation implements IOperation {
  private final LinkedList<IOperationListener> listeners;
  private MethodNode operationNode;
  private Image icon;
  private String name;

  public Operation() {
    listeners = new LinkedList<IOperationListener>();
  }

  public LinkedList<IOperationListener> getListeners() {
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
}
