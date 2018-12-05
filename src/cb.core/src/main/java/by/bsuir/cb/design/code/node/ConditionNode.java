package by.bsuir.cb.design.code.node;

import by.bsuir.cb.design.code.IGenerative;
import by.bsuir.cb.design.code.IScopable;
import by.bsuir.cb.design.code.Keyword;
import by.bsuir.cb.design.ui.method.TreeViewDecorator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ConditionNode extends TemplateNode implements IScopable {
  private List<IGenerative> children = new LinkedList<>();

  @Override
  public String toCodeString() {
    String code = getTemplate();
    int i = 0;
    for (Entry<String, IGenerative> entry : getKeywordMap().entrySet()) {
      if (entry.getValue() instanceof Keyword) {
        var keyCode = entry.getValue().toCodeString();
        if (!keyCode.isEmpty()) {
          code = code.replaceAll("\\$\\{" + entry.getKey() + "\\}", keyCode);
        }
      } else {
        int j = i + 1;
        StringBuilder builder = new StringBuilder();
        for (; j < children.size(); j++) {
          if (((TreeViewDecorator) children.get(j)).getSource() instanceof BeginNode) {
            i = j;
            break;
          }
          builder.append(children.get(j).toCodeString());
        }
        code = code.replaceAll("\\$\\{" + entry.getKey() + "\\}", builder.toString());
      }
    }
    return code;
  }

  public ConditionNode(IGenerative parent) {
    super(parent);
  }

  @Override
  public void addChild(int index, IGenerative child) {
    children.add(index, child);
  }

  @Override
  public void appendChild(IGenerative child) {
    children.add(child);
  }

  @Override
  public void removeChild(IGenerative child) {
    children.remove(child);
  }

}
