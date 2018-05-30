package cb.core.ui.design;

import org.eclipse.swt.widgets.Composite;

public interface IDesignViewPart {
  Composite getGUI();
  void buildGUI();
  void setParent(Composite parent);
  Composite getParent();

}
