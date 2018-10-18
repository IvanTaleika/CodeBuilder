package cb.core.editors.designEditor;

import org.eclipse.osgi.util.NLS;

public class DesignPageMessages extends NLS {
  private static final String BASE_NAME = "cb.core.editors.designEditor.designPageMessages"; //$NON-NLS-1$
  public static String Structure_title;
  public static String GenerateButton_ToolTip;

  static {
    NLS.initializeMessages(BASE_NAME, DesignPageMessages.class);
  }
  private DesignPageMessages() {
        
  }
}
