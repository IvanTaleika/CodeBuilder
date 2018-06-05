package cb.core.code.utils;

import cb.core.code.parsers.template.ITemplateParser;
import cb.core.code.parsers.template.JavaTemplateParser;
import cb.core.exceptions.CBException;

public final class CodeUtilsProvider {
  private static final String JAVA_FILE = ".java";



  private static String fileType;
  private static ITemplateParser templateParser;

  // TODO change language attribute (not file extension)
  public CodeUtilsProvider(String sourceCodeExtention) throws CBException {
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
