package cb.core.code.parsers.template;

import java.util.LinkedList;

public interface ITemplateParser {
  LinkedList<String> getKeywords(String template);
}
