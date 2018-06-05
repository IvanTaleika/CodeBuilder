package cb.core.code.generators;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaCodeGenerator implements ICodeGenerator {

//TODO NO HARD CODE
  @Override
  public String generate(String template, HashMap<String, String> values) {
    if(template == null || values == null) {
      return "";
    }

    for (Map.Entry<String, String> entry : values.entrySet()) {
      String key =  "\\$\\{" + entry.getKey() + "\\}";
      Pattern pattern = Pattern.compile(key);
      Matcher matcher = pattern.matcher(template);
      template = matcher.replaceAll(entry.getValue());
      
    }
    return template;
  }

}
