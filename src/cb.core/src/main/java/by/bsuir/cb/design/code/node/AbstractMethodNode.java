package by.bsuir.cb.design.code.node;

import by.bsuir.cb.design.code.IGenerative;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractMethodNode implements IGenerative {
  @EqualsAndHashCode.Include
  private boolean dirty;
  private IGenerative parent;

  public AbstractMethodNode(IGenerative parent) {
    this.parent = parent;
  }
}
