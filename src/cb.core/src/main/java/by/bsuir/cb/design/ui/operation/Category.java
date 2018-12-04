package by.bsuir.cb.design.ui.operation;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
  @EqualsAndHashCode.Include
  private String title;
  private List<Operation> operations = new LinkedList<>();

}
