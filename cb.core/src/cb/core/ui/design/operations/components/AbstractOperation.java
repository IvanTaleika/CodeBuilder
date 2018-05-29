package cb.core.ui.design.operations.components;

import java.util.LinkedList;

public abstract class AbstractOperation implements Operation {
  LinkedList<OperationListener> listeners = new LinkedList<OperationListener>();



  public void addListener(OperationListener listener) {
    listeners.add(listener);
  }

  public void removeListener(OperationListener listener) {
    listeners.remove(listener);
  }
}
