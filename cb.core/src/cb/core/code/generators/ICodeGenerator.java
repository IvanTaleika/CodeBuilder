package cb.core.code.generators;

import java.util.HashMap;

public interface ICodeGenerator {
  StringBuffer generate(String template, HashMap<String, String> values);
}
