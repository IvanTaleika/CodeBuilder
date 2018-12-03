package by.bsuir.cb.design.ui.outline;

import by.bsuir.cb.CodeBuilder;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class DesignEditorOutlineContentProvider implements ITreeContentProvider {
  private static final ILog LOGGER = CodeBuilder.getDefault().getLog();

  @Override
  public Object[] getElements(Object inputElement) {
    return getChildren(inputElement);
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    if (parentElement instanceof ICompilationUnit) {
      var compilationUnit = (ICompilationUnit) parentElement;
      try {
        return compilationUnit.getTypes();
      } catch (JavaModelException e) {
        LOGGER.log(new Status(Status.ERROR, CodeBuilder.PLUGIN_ID, e.getMessage(), e));
      }
    } else if (parentElement instanceof IType) {
      var type = (IType) parentElement;

      try {
        return type.getChildren();
      } catch (JavaModelException e) {
        LOGGER.log(new Status(Status.ERROR, CodeBuilder.PLUGIN_ID, e.getMessage(), e));
      }
    }
    return new Object[] {};
  }

  @Override
  public Object getParent(Object element) {
    var parent = ((IJavaElement) element).getParent();
    if (parent instanceof ICompilationUnit) {
      return null;
    } else {
      return parent;
    }
  }

  @Override
  public boolean hasChildren(Object element) {
    return getChildren(element).length > 0;
  }

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    // TODO Auto-generated method stub
    ITreeContentProvider.super.inputChanged(viewer, oldInput, newInput);
  }

}
