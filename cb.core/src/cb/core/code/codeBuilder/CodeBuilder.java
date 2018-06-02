package cb.core.code.codeBuilder;

import cb.core.code.parsers.template.ITemplateParser;
import cb.core.code.parsers.template.JavaTemplateParser;
import cb.core.exceptions.CBException;

public final class CodeBuilder {
  private static final String JAVA_FILE = ".java";

  public final static String CONDITION = "condition";
  public final static String FUNCTION = "function";
  public final static String RETURN = "return";

  private static String fileType;
  private static ITemplateParser templateParser;

  // TODO change language attribute (not file extension)
  public CodeBuilder(String sourceCodeExtention) throws CBException {
    switch (sourceCodeExtention) {
      case JAVA_FILE:
        fileType = JAVA_FILE;
        templateParser = new JavaTemplateParser();

        break;

      default:
        // TODO move error to .properties file
        throw new CBException("Unknown source file type: " + sourceCodeExtention);
    }
  }

  public static ITemplateParser getTemplateParser() {
    return templateParser;
  }

  public static String getFileType() {
    return fileType;
  }


}
