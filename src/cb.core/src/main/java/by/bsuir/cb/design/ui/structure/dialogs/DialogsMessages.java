package by.bsuir.cb.design.ui.structure.dialogs;

import org.eclipse.osgi.util.NLS;

public class DialogsMessages extends NLS {
  private static final String BUNDLE_NAME =
      "by.bsuir.cb.design.ui.structure.dialogs.dialogsMessages"; //$NON-NLS-1$
  public static String AddMethodDialog_Title;
  public static String AddMethodDialog_ShellTitle;
  public static String AddMethodDialog_Message;
  public static String AddMethodDialog_AccessLabel;
  public static String AddMethodDialog_ReturnTypeLabel;
  public static String AddMethodDialog_NameLabel;
  public static String AddMethodDialog_MathodVariablesLabel;
  public static String AddVariableDialog_Title;
  public static String AddVariableDialog_ShellTitle;
  public static String AddVariableDialog_Message;
  public static String AddVariableDialog_AccessLabel;
  public static String AddVariableDialog_ReturnTypeLabel;
  public static String AddVariableDialog_NameLabel;
  public static String CustomizeNodeDialog_Title;
  public static String CustomizeNodeDialog_ShellTitle;
  public static String CustomizeNodeDialog_Message;
  public static String CustomizeNodeDialog_LabelDefaultMessage;
  public static String TypeChooseButton_Text;
  public static String TypeDialog_Title;
  public static String TypeDialog_Message;
  public static String AddMethodDialog_txtR_text;
  public static String AddMethodDialog_btnNewButton_text;

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
