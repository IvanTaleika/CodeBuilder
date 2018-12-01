package by.bsuir.cb.design.code;

import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class Formatter implements IFormatter {


  @Override
  @SuppressWarnings("unchecked")
  public String formateCode(String source) {
    @SuppressWarnings("rawtypes")
    Map options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();

    options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_10);
    options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_10);
    options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_10);

    // change the option to wrap each enum constant on a new line
    options.put(DefaultCodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ENUM_CONSTANTS,
        DefaultCodeFormatterConstants.createAlignmentValue(true,
            DefaultCodeFormatterConstants.WRAP_ONE_PER_LINE,
            DefaultCodeFormatterConstants.INDENT_ON_COLUMN));

    // instantiate the default code formatter with the given options
    final CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(options);

    final TextEdit edit = codeFormatter.format(CodeFormatter.K_CLASS_BODY_DECLARATIONS, // format a
        source, // source to format
        0, // starting position
        source.length(), // length
        0, // initial indentation
        System.getProperty("line.separator") // line separator
    );
    if (edit == null) {
      return source;
    }

    IDocument document = new Document(source);
    try {
      edit.apply(document);
    } catch (MalformedTreeException | BadLocationException e) {
      e.printStackTrace();
    }
    return document.get();
  }

}
