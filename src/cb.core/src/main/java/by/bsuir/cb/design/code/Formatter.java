package by.bsuir.cb.design.code;

import by.bsuir.cb.CodeBuilder;
import java.util.Map;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class Formatter implements IFormatter {
  private static final ILog LOGGER = CodeBuilder.getDefault().getLog();

  @Override
  public String formateCode(String source) {
    @SuppressWarnings("rawtypes")
    Map options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();

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
      LOGGER.log(new Status(IStatus.ERROR, CodeBuilder.PLUGIN_ID, e.getMessage(), e));
    }
    return document.get();
  }

}
