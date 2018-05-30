// TODO change package
package cb.core.ui.design.operations.components;

public interface Operation {

  boolean isSelected();

  void setSelection(boolean isSelected);

  void addListener(IOperationListener listener);

  void removeListener(IOperationListener listener);

  // TODO public String(?) getCodeTemplate()
  // TODO public String(?) getImage()


}
