package cb.core.code.parsers.template;

import java.util.Set;

public interface ITemplateParser {
  Set<String> getKeywords(String template);
}
