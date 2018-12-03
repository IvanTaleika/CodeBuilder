package by.bsuir.cb;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;

/**
 * Provider for plugin's bundle resources.
 */
public final class BundleResourceProvider {
  public static final String ICONS_FOLDER = "icons/";
  private static final Bundle bundle = CodeBuilder.getDefault().getBundle();
  private static final Map<URL, Image> cashedImages = new HashMap<>();
  private static final Map<URL, ImageDescriptor> cashedImageDescriptors = new HashMap<>();

  /**
   * Creates cleanup hook to dispose all images when plugin is uninstalled (e.g. deleted or Eclipse
   * closed).
   * 
   * @param context plugin's bundle context
   */
  public static void configureCleanUp(BundleContext context) {
    context.addBundleListener(event -> {
      if (event.getType() == BundleEvent.UNINSTALLED) {
        if (!cashedImages.isEmpty()) {
          Display.getDefault().asyncExec(BundleResourceProvider::disposeImages);
        }
      }
    });
  }

  /**
   * Returns {@link Image} for given path. Every image's instance are cashed and will be disposed
   * when {@link BundleEvent#UNINSTALLED} event fires.
   * 
   * @param imagePath image's path
   * @return the Image.
   */
  public static Image getImage(String imagePath) {
    Path path = new Path(ICONS_FOLDER + imagePath);
    final URL url = FileLocator.find(bundle, path, null);
    if (url == null) {
      return null;
    }
    Image image = cashedImages.get(url);
    if (image != null) {
      return image;
    }
    ImageDescriptor imageDescriptor = cashedImageDescriptors.get(url);
    if (imageDescriptor != null) {
      image = imageDescriptor.createImage();
    } else {
      imageDescriptor = ImageDescriptor.createFromURL(url);
      cashedImageDescriptors.put(url, imageDescriptor);
      image = imageDescriptor.createImage();
    }
    cashedImages.put(url, image);
    return image;
  }

  /**
   * Returns {@link ImageDescriptor} for the given path. Caches all descriptors.
   * 
   * @param imagePath image's path
   * @return the ImageDescriptor for image at the imagePath
   */
  public static ImageDescriptor getImageDescriptor(String imagePath) {
    Path path = new Path(ICONS_FOLDER + imagePath);
    final URL url = FileLocator.find(bundle, path, null);
    ImageDescriptor imageDescriptor =
        cashedImageDescriptors.computeIfAbsent(url, ImageDescriptor::createFromURL);
    return imageDescriptor;
  }

  public static File getFile(String classpath) throws IOException {
    URL url = getUrl(classpath);
    return new File(FileLocator.toFileURL(url).getFile());
  }

  public static URL getUrl(String classpath) {
    return FileLocator.find(bundle, new Path(classpath), null);
  }

  private static void disposeImages() {
    for (Image image : cashedImages.values()) {
      if (image != null && !image.isDisposed()) {
        image.dispose();
      }
    }
  }

  private BundleResourceProvider() {

  }



}
