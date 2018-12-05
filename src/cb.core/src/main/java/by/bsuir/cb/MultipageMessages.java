package by.bsuir.cb;

import org.eclipse.osgi.util.NLS;

public class MultipageMessages extends NLS {
  private static final String BASE_NAME = "by.bsuir.cb.multipageMessages"; //$NON-NLS-1$
  public static String FirstPage;
  public static String SecondPage;
  public static String CreationError;
  public static String InputError;
  public static String SaveTitle;
  public static String SaveMessage;

  static {
    NLS.initializeMessages(BASE_NAME, MultipageMessages.class);
  }

  private MultipageMessages() {

  }
}
