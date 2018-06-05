package cb.core.editors.designEditor.node;

import java.util.List;
import org.eclipse.swt.widgets.Widget;

public interface IWidgetedNode {
  List<Widget> getNodeWidgets();
  void addNodeWidget(Widget widget);
}
