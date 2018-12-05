package by.bsuir.cb.design.code.node;

import by.bsuir.cb.design.code.IGenerative;
import by.bsuir.cb.design.code.Keyword;
import java.util.HashMap;
import java.util.Map.Entry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EndNode extends TemplateNode {

  public EndNode(IGenerative parent) {
    super(parent);
    setTemplate("return ${returnValue};");
    var map = new HashMap<String, IGenerative>();
    var keyword = new Keyword();
    keyword.setName("returnValue");
    keyword.setType("*");
    map.put("returnValue", keyword);
    setKeywordMap(map);
  }

  @Override
  public String toCodeString() {
    String code = getTemplate();
    for (Entry<String, IGenerative> entry : getKeywordMap().entrySet()) {
      var keyCode = entry.getValue().toCodeString();
      if (!keyCode.isEmpty()) {
        code = code.replaceAll("\\$\\{" + entry.getKey() + "\\}", keyCode);
      } else {
        code = code.replaceAll("\\$\\{" + entry.getKey() + "\\}", "null");
      }
    }
    return code;
  }
}
