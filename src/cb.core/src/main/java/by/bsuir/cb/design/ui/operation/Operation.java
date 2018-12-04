package by.bsuir.cb.design.ui.operation;

import by.bsuir.cb.design.code.IGenerative;
import lombok.Data;

@Data
public class Operation {
  private IGenerative methodNode;
  private String name;
  private String description;
  private String iconPath;
}
