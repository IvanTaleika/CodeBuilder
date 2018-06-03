package cb.core.code.parsers.template;

import java.util.HashSet;

public interface ITemplateParser {
  HashSet<String> getKeywords(String template);
}
