package by.bsuir.cb.design;

import org.eclipse.osgi.util.NLS;

public class DesignEditorMessages extends NLS {
  private static final String BASE_NAME =
      "by.bsuir.cb.design.designEditorMessages"; //$NON-NLS-1$
  public static String Structure_Title;
  public static String GenerateButton_ToolTip;
  public static String XmlOperationsErrorDialog_Message;
  public static String XmlOperationsErrorDialog_Title;

  static {
    NLS.initializeMessages(BASE_NAME, DesignEditorMessages.class);
  }

  private DesignEditorMessages() {

  }
}
