package cb.core.ui.design;

import org.eclipse.swt.widgets.Composite;
import cb.core.exceptions.CBResourceException;

public interface IDesignViewPart {
  Composite getGUI();
  void buildGUI() throws CBResourceException;
  void setParent(Composite parent);
  Composite getParent();

}
