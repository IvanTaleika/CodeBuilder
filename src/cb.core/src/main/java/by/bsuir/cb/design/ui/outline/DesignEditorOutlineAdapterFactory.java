package by.bsuir.cb.design.ui.outline;

import by.bsuir.cb.design.DesignEditor;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class DesignEditorOutlineAdapterFactory implements IAdapterFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
    if (IContentOutlinePage.class.equals(adapterType)) {
      return (T) new DesignEditorOutlinePage((DesignEditor) adaptableObject);
    }
    return null;
  }

  @Override
  public Class<?>[] getAdapterList() {
    return new Class<?>[] {DesignEditorOutlinePage.class};
  }

}
