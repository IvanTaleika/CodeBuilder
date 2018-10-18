package cb.core.editors;

import org.eclipse.osgi.util.NLS;

public class MultipageMessages extends NLS {
  private static final String BASE_NAME = "cb.core.editors.multipageMessages"; //$NON-NLS-1$
  public static String FirstPage;
  public static String SecondPage;
  public static String CreationError;
  public static String InputError;
  static {
    NLS.initializeMessages(BASE_NAME, MultipageMessages.class);
  }
  private MultipageMessages() {
    
  }
}
