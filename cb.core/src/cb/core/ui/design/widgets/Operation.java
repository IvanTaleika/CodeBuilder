//TODO change package 
package cb.core.ui.design.widgets;

import java.util.ArrayList;

public interface Operation {
  ArrayList<OperationSubsriber> subsribers = new ArrayList<OperationSubsriber>();

  boolean isSelected();

  void setSelection(boolean isSelected);

  default void subsribe(OperationSubsriber subsriber) {
    subsribers.add(subsriber);
  }

  default void unsubsribe(OperationSubsriber subsriber) {
    subsribers.remove(subsriber);
  }
  
  // TODO public String(?) getCodeTemplate()
  // TODO public String(?) getImage()


}
