package cb.core.ui.design.operations.components;

import java.util.LinkedList;

public abstract class AbstractOperation implements Operation {
  LinkedList<IOperationListener> listeners = new LinkedList<IOperationListener>();



  public void addListener(IOperationListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IOperationListener listener) {
    listeners.remove(listener);
  }
}
