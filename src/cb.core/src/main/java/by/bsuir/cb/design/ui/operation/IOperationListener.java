package by.bsuir.cb.design.ui.operation;

public interface IOperationListener {

  /**
   * Called when selection {@link OperationPicker}'s selection changed.
   *
   * @param selection selected {@link Operation} if any, {@code null} otherwise
   */
  void operationSelectionChanged(Operation selection);
}
