package cb.core.ui.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.osgi.framework.BundleListener;
import cb.core.Activator;

// TODO make BundleResourceProvider (return resourceProvider for each bundle (plugin)) - non static
public final class BundleResourceProvider {
  private static final Bundle bundle = Activator.getDefault().getBundle();
  private static final Map<URL, Image> cashedImages = new HashMap<>();
  private static final Map<URL, ImageDescriptor> cashedImageDescriptors = new HashMap<>();



  // FIXME never get event UNINSTALLED
  public static void configureCleanUp(BundleContext context) {
    context.addBundleListener(new BundleListener() {
      public void bundleChanged(BundleEvent event) {
        if (event.getType() == BundleEvent.UNINSTALLED) {
          if (!cashedImages.isEmpty()) {
            Display.getDefault().asyncExec(new Runnable() {
              public void run() {
                disposeImages();
              }
            });
          }
        }
      }
    });
  }

  public static Image getImage(String imageClasspath) {
    // TODO check if path is a full path
    Path path = new Path(imageClasspath);
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
      // TODO check if it imageDescriptor is valid
      cashedImageDescriptors.put(url, imageDescriptor);
      image = imageDescriptor.createImage();
    }
    cashedImages.put(url, image);
    return image;
  }

  /**
   * @return the {@link Image}, with caching.
   */
//  public static Image getImage(String path) {
//    path = normalizePath(path);
//    URL url;
//    Image image = cashedImages.get(path);
//    if (image == null) {
//      InputStream is = getFile(path);
//      try {
//        image = new Image(Display.getCurrent(), is);
//        cashedImages.put(path, image);
//      } finally {
//        IOUtils.closeQuietly(is);
//      }
//    }
//    return image;
//  }

  // /**
  // * @return the {@link ImageDescriptor}, with caching.
  // */
  // public static ImageDescriptor getImageDescriptor(String path) {
  // path = normalizePath(path);
  // ImageDescriptor descriptor = cashedImageDescriptors.get(path);
  // if (descriptor == null) {
  // Image image = getImage(path);
  // descriptor = new ImageImageDescriptor(image);
  // m_pathToImageDescriptor.put(path, descriptor);
  // }
  // return descriptor;
  // }

  public static ImageDescriptor getImageDescriptor(String imageClasspath) {
    // TODO check if path is a full path
    Path path = new Path(imageClasspath);
    final URL url = FileLocator.find(bundle, path, null);
    ImageDescriptor imageDescriptor = cashedImageDescriptors.get(url);
    if (imageDescriptor == null) {
      imageDescriptor = ImageDescriptor.createFromURL(url);
      // TODO check if it imageDescriptor is valid
      cashedImageDescriptors.put(url, imageDescriptor);
    }
    return imageDescriptor;
  }

  public static Bundle getBundle() {
    return bundle;
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
