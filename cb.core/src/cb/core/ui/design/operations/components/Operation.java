// TODO change package
package cb.core.ui.design.operations.components;

public interface Operation {

  boolean isSelected();

  void setSelection(boolean isSelected);

  void addListener(OperationListener listener);

  void removeListener(OperationListener listener);

  // TODO public String(?) getCodeTemplate()
  // TODO public String(?) getImage()


}
