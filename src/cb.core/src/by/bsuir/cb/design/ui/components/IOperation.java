package by.bsuir.cb.design.ui.components;

import by.bsuir.cb.design.code.node.MethodNode;

import org.eclipse.swt.graphics.Image;

// TODO move node from Operation
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
