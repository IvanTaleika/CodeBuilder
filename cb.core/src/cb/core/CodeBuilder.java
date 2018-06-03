package cb.core;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import cb.core.ui.utils.BundleResourceProvider;

/**
 * The activator class controls the plug-in life cycle
 */
public class CodeBuilder extends AbstractUIPlugin {
  public static final String IMAGES_FOLDER = "images/";

  // The plug-in ID
  public static final String PLUGIN_ID = "cb.core"; //$NON-NLS-1$

  // The shared instance
  private static CodeBuilder plugin;

  /**
   * The constructor
   */
  public CodeBuilder() {}

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;
    BundleResourceProvider.configureCleanUp(context);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance
   *
   * @return the shared instance
   */
  public static CodeBuilder getDefault() {
    return plugin;
  }
  
  public static Image getImage(String name) {
    return BundleResourceProvider.getImage(IMAGES_FOLDER + name);
  }

}
