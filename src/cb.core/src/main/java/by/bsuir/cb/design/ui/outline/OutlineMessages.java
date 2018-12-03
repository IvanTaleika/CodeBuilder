package by.bsuir.cb.design.ui.outline;

import org.eclipse.osgi.util.NLS;

public class OutlineMessages extends NLS {
  private static final String BUNDLE_NAME =
      "by.bsuir.cb.design.ui.outline.outlineMessages"; //$NON-NLS-1$
  public static String Menu_newMethod;
  public static String Menu_newVariable;
  public static String Menu_delete;

  private OutlineMessages() {}


  static {
    NLS.initializeMessages(BUNDLE_NAME, OutlineMessages.class);
  }
}
