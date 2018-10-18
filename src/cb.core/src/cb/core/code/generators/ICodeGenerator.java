package cb.core.code.generators;

import java.util.HashMap;

public interface ICodeGenerator {
  String generate(String template, HashMap<String, String> values);
}
