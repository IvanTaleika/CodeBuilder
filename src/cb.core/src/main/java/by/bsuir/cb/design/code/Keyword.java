package by.bsuir.cb.design.code;

import lombok.Data;

@Data
public class Keyword implements IGenerative {
  private String type;
  private String name;
  private String value;

  @Override
  public String toCodeString() {
    return value == null ? "" : value;
  }

  @Override
  public IGenerative getParent() {
    return null;
  }

  @Override
  public boolean isDirty() {
    return false;
  }

  @Override
  public void setParent(IGenerative parent) {}

  @Override
  public void setDirty(boolean dirty) {}

}
