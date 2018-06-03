package cb.core.code.parsers.template;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaTemplateParser implements ITemplateParser {
  private final String KEYWORD_REGEX = "\\$\\{(.*?)\\}";
  

  @Override
  public HashSet<String> getKeywords(String template) {
    Pattern pattern = Pattern.compile(KEYWORD_REGEX);
    Matcher matcher = pattern.matcher(template);
    HashSet<String> keywords = new HashSet<>();
    while (matcher.find()) {
      keywords.add(matcher.group(1));
      
    }
    return keywords;
  }
}
