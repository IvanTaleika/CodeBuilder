package by.bsuir.cb.design.code.node;

import by.bsuir.cb.design.code.IGenerative;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TemplateNode extends AbstractMethodNode {
  private String template;
  private Map<String, IGenerative> keywordMap;

  public TemplateNode(IGenerative parent) {
    super(parent);
  }

  @Override
  public String toCodeString() {
    String code = template;
    for (Entry<String, IGenerative> entry : keywordMap.entrySet()) {
      var keyCode = entry.getValue().toCodeString();
      if (!keyCode.isEmpty()) {
        code = code.replaceAll("\\$\\{" + entry.getKey() + "\\}", keyCode);
      }
    }
    return code;
  }
}
