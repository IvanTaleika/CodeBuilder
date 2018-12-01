package by.bsuir.cb.design.ui.operation.xml;

/**
 * Throws when error occurs during the XML parsing.
 */
public class XmlParsingException extends Exception {
  private static final long serialVersionUID = 787617164731343845L;

  public XmlParsingException() {
    super();
  }

  public XmlParsingException(final String message) {
    super(message);
  }

  public XmlParsingException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public XmlParsingException(final Throwable cause) {
    super(cause);
  }
}
