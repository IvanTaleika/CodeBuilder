package by.bsuir.cb.design.code.node;

import by.bsuir.cb.design.code.IGenerative;
import by.bsuir.cb.design.code.IScopable;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ConditionNode extends TemplateNode implements IScopable {
  private List<IGenerative> children = new LinkedList<>();

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
