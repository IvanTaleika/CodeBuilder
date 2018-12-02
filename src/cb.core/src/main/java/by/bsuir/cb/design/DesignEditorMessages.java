package by.bsuir.cb.design;

import org.eclipse.osgi.util.NLS;

public class DesignPageMessages extends NLS {
  private static final String BASE_NAME =
      "by.bsuir.cb.design.designPageMessages"; //$NON-NLS-1$
  public static String Structure_title;
  public static String GenerateButton_ToolTip;

  static {
    NLS.initializeMessages(BASE_NAME, DesignPageMessages.class);
  }

  private DesignPageMessages() {

  }
}
