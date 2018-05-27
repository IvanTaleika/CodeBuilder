package cb.core.ui.editors.designPage.operations;

import org.eclipse.osgi.util.NLS;

public class OperationWidgetsText extends NLS {
  private static final String BASE_NAME = "cb.core.ui.editors.designPage.operations.operationWidgetsText"; //$NON-NLS-1$
  public static String Operations_title;
  public static String Multipage_SecondPage;
  public static String Multipage_creationError;
  public static String Multipage_inputRegularExpretionPattern;
  public static String Multipage_inputError;
  static {
    NLS.initializeMessages(BASE_NAME, OperationWidgetsText.class);
  }
  private OperationWidgetsText() {
        
  }
}
