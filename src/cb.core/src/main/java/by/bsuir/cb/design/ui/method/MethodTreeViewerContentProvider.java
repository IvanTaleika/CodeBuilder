package by.bsuir.cb.design.ui.method;

import by.bsuir.cb.design.code.IGenerative;
import by.bsuir.cb.design.code.IScopable;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class MethodTreeViewerContentProvider implements ITreeContentProvider {

  @Override
  public Object[] getElements(Object inputElement) {
    return getChildren(inputElement);
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    var source = ((TreeViewDecorator) parentElement).getSource();
    if (source instanceof IScopable) {
      return ((IScopable) source).getChildren().toArray();
    }
    return new Object[] {};
  }

  @Override
  public Object getParent(Object element) {
    return ((IGenerative) element).getParent();
  }

  @Override
  public boolean hasChildren(Object element) {
    return getChildren(element).length > 0;
  }

}
