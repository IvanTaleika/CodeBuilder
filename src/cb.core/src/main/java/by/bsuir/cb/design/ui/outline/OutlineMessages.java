package by.bsuir.cb.design.ui.outline;

import org.eclipse.osgi.util.NLS;

public class OutlineMessages extends NLS {
  private static final String BUNDLE_NAME =
      "by.bsuir.cb.design.ui.outline.outlineMessages"; //$NON-NLS-1$

  public static String AddMethodDialog_NameLabel;
  public static String AddMethodDialog_Title;
  public static String AddMethodDialog_ShellTitle;
  public static String AddMethodDialog_btnNewButton_text;
  public static String AddMethodDialog_MathodVariablesLabel;
  public static String AddMethodDialog_Message;
  public static String AddMethodDialog_ReturnTypeLabel;
  public static String AddVariableDialog_ReturnTypeLabel;
  public static String AddVariableDialog_ShellTitle;
  public static String AddVariableDialog_AccessLabel;
  public static String AddVariableDialog_NameLabel;
  public static String AddVariableDialog_Message;
  public static String AddVariableDialog_Title;
  public static String AddClassVariableDialog_ValueLabel;
  public static String DeleteDialog_TitleGeneric;
  public static String DeleteDialog_MessageGeneric;
  public static String TypeChooseButton_Text;
  public static String Menu_newMethod;
  public static String Menu_newVariable;
  public static String Menu_delete;
  public static String AccessLabel;

  private OutlineMessages() {}


  static {
    NLS.initializeMessages(BUNDLE_NAME, OutlineMessages.class);
  }
}
