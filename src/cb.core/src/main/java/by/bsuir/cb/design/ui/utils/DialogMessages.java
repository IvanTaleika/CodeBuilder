package by.bsuir.cb.design.ui.utils;

import org.eclipse.osgi.util.NLS;

public class DialogMessages extends NLS {
  private static final String BUNDLE_NAME =
      "by.bsuir.cb.design.ui.utils.dialogMessages"; //$NON-NLS-1$
  public static String TypeDialog_Title;
  public static String TypeDialog_Message;

  private DialogMessages() {}

  static {
    NLS.initializeMessages(BUNDLE_NAME, DialogMessages.class);
  }
}
