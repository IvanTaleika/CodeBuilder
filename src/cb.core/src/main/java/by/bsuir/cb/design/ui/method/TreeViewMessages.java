package by.bsuir.cb.design.ui.method;

import org.eclipse.osgi.util.NLS;

public class TreeViewMessages extends NLS {
  private static final String BUNDLE_NAME =
      "by.bsuir.cb.design.ui.method.treeViewMessages"; //$NON-NLS-1$

  public static String CustomizeNodeDialog_Title;
  public static String CustomizeNodeDialog_ShellTitle;
  public static String CustomizeNodeDialog_Message;
  public static String CustomizeNodeDialog_LabelDefaultMessage;
  public static String CustomizeNodeDialog_lblNewLabel_text;
  public static String CustomizeNodeDialog_TypeMismatchMessage;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  private TreeViewMessages() {
    // do not instantiate
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Class initialization
  //
  ////////////////////////////////////////////////////////////////////////////
  static {
    // load message values from bundle file
    NLS.initializeMessages(BUNDLE_NAME, TreeViewMessages.class);
  }
}
