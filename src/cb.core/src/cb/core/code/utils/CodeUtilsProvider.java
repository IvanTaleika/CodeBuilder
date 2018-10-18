package cb.core.code.utils;

import cb.core.code.generators.ICodeGenerator;
import cb.core.code.generators.JavaCodeGenerator;
import cb.core.code.parsers.template.ITemplateParser;
import cb.core.code.parsers.template.TemplateParser;
import cb.core.exceptions.CBException;

public final class CodeUtilsProvider {
  private static final String JAVA_FILE = ".java";


  private static ICodeGenerator codeGenerator;
  private static String fileType;
  private static ITemplateParser templateParser;

  // TODO change language attribute (not file extension)
  public CodeUtilsProvider(String sourceCodeExtention) throws CBException {
    switch (sourceCodeExtention) {
      case JAVA_FILE:
        fileType = JAVA_FILE;
        templateParser = new TemplateParser();
        codeGenerator = new JavaCodeGenerator();

        break;

      default:
        // TODO move exceptions to .properties file
        throw new CBException("Unknown source file type: " + sourceCodeExtention);
    }
  }

  public static ITemplateParser getTemplateParser() {
    return templateParser;
  }

  public static String getFileType() {
    return fileType;
  }

  public static ICodeGenerator getCodeGenerator() {
    return codeGenerator;
  }



}
