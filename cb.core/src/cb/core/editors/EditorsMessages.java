package cb.core.editors;

import org.eclipse.osgi.util.NLS;

public class EditorsMessages extends NLS {
  private static final String BASE_NAME = "cb.core.ui.editors.editorMessages"; //$NON-NLS-1$
  public static String Multipage_FirstPage;
  public static String Multipage_SecondPage;
  public static String Multipage_creationError;
  public static String Multipage_inputRegularExpretionPattern;
  public static String Multipage_inputError;
  static {
    NLS.initializeMessages(BASE_NAME, EditorsMessages.class);
  }
  private EditorsMessages() {
    
  }
}
