package by.bsuir.cb;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class CodeBuilder extends AbstractUIPlugin {

  /** The plagin's ID. */
  public static final String PLUGIN_ID = "cb.core"; //$NON-NLS-1$

  /** The plugin shared instance. */
  private static CodeBuilder plugin;

  /**
   * The constructor.
   */
  public CodeBuilder() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;
    BundleResourceProvider.configureCleanUp(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance of plugin.
   *
   * @return the shared instance
   */
  public static CodeBuilder getDefault() {
    return plugin;
  }

}
