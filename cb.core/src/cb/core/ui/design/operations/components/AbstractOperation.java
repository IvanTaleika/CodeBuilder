package cb.core.ui.design.operations.components;

import java.util.LinkedList;
import org.w3c.dom.Element;
import cb.core.code.node.MethodNode;
import cb.core.code.node.NodeFactory;
import cb.core.exceptions.CBResourceException;

public abstract class AbstractOperation implements Operation {
  private final LinkedList<IOperationListener> listeners;
  private MethodNode operationNode;

  public AbstractOperation(Element nodeData) throws CBResourceException {
    this.operationNode = NodeFactory.create(nodeData);
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

}
