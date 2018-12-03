package by.bsuir.cb.design.ui.structure.dialogs;

import org.eclipse.osgi.util.NLS;

public class DialogsMessages extends NLS {
  private static final String BUNDLE_NAME =
      "by.bsuir.cb.design.ui.structure.dialogs.dialogsMessages"; //$NON-NLS-1$

  public static String CustomizeNodeDialog_Title;
  public static String CustomizeNodeDialog_ShellTitle;
  public static String CustomizeNodeDialog_Message;
  public static String CustomizeNodeDialog_LabelDefaultMessage;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  private DialogsMessages() {
    // do not instantiate
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Class initialization
  //
  ////////////////////////////////////////////////////////////////////////////
  static {
    // load message values from bundle file
    NLS.initializeMessages(BUNDLE_NAME, DialogsMessages.class);
  }
}
