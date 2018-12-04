package by.bsuir.cb.design.code.node;

import by.bsuir.cb.design.code.IGenerative;
import java.util.HashMap;
import java.util.Map.Entry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EndNode extends AbstractMethodNode {
  private String template;
  private HashMap<String, IGenerative> keywordMap;

  public EndNode(IGenerative parent) {
    super(parent);
  }

  @Override
  public String toCodeString() {
    String code = "";
    for (Entry<String, IGenerative> entry : keywordMap.entrySet()) {
      if (entry.getValue() != null) {
        code = template.replaceAll(entry.getKey(), entry.getValue().toCodeString());
      }
    }
    return code;
  }
}
