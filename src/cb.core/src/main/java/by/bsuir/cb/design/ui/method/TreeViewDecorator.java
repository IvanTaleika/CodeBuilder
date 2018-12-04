package by.bsuir.cb.design.ui.method;

import by.bsuir.cb.design.code.IGenerative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeViewDecorator implements IGenerative {
  private IGenerative source;
  private String iconPath;
  private String name;

  @Override
  public String toCodeString() {
    return source.toCodeString();
  }

  @Override
  public IGenerative getParent() {
    return source.getParent();
  }

  @Override
  public boolean isDirty() {
    return source.isDirty();
  }

}
