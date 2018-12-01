package by.bsuir.cb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates opened file to know if it can be edited with the editor.
 */
public class FileValidator {
  private static final String EXTENTION_REGEX = "(?i)\\.[0-9a-z]+$";
  private static final String JAVA_EXTENTION = ".java";

  /**
   * Validates file's extension.
   *
   * @param filename the full file name
   * @return true, if successful
   */
  public static boolean validateExtension(String filename) {
    Pattern pattern = Pattern.compile(EXTENTION_REGEX);
    Matcher matcher = pattern.matcher(filename);
    if (matcher.find()) {
      // TODO check group index
      return matcher.group(0).equals(JAVA_EXTENTION);
    } else {
      return false;
    }
  }
}
