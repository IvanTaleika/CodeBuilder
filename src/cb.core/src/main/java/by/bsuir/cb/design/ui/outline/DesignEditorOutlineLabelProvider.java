package by.bsuir.cb.design.ui.outline;

import by.bsuir.cb.BundleResourceProvider;
import by.bsuir.cb.CodeBuilder;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class DesignEditorOutlineLabelProvider implements ILabelProvider {
  private static final ILog LOGGER = CodeBuilder.getDefault().getLog();

  public static final String TYPE_ICON_PATH = "outline/class_obj.gif";
  public static final String METHOD_ICON_PATH = "outline/methpub_obj.gif";
  public static final String FIELD_ICON_PATH = "outline/field_public_obj.gif";

  @Override
  public void addListener(ILabelProviderListener listener) {}

  @Override
  public void dispose() {}

  @Override
  public boolean isLabelProperty(Object element, String property) {
    return false;
  }

  @Override
  public void removeListener(ILabelProviderListener listener) {}

  @Override
  public Image getImage(Object element) {
    if (element instanceof IType) {
      return BundleResourceProvider.getImage(TYPE_ICON_PATH);
    } else if (element instanceof IMethod) {
      return BundleResourceProvider.getImage(METHOD_ICON_PATH);
    } else if (element instanceof IField) {
      return BundleResourceProvider.getImage(FIELD_ICON_PATH);
    }
    return null;
  }

  @Override
  public String getText(Object element) {
    if (element instanceof IMethod) {
      return getMehodText((IMethod) element);
    } else if (element instanceof IField) {
      return getFieldText((IField) element);
    } else {
      return ((IJavaElement) element).getElementName();
    }
  }

  private String getMehodText(IMethod method) {
    StringBuilder builder = new StringBuilder(method.getElementName());
    try {
      builder.append('(');
      var parameters = method.getParameterTypes();
      for (int i = 0; i < parameters.length; i++) {
        if (i != 0) {
          builder.append(", ");
        }
        builder.append(Signature.getSignatureSimpleName(parameters[i]));
      }
      builder.append(") : ");
      builder.append(Signature.getSignatureSimpleName(method.getReturnType()));
      return builder.toString();
    } catch (IllegalArgumentException | JavaModelException e) {
      LOGGER.log(new Status(Status.ERROR, CodeBuilder.PLUGIN_ID, e.getMessage(), e));
    }
    return null;
  }

  private String getFieldText(IField field) {
    try {
      return field.getElementName() + " : "
          + Signature.getSignatureSimpleName((field).getTypeSignature());
    } catch (JavaModelException e) {
      LOGGER.log(new Status(Status.ERROR, CodeBuilder.PLUGIN_ID, e.getMessage(), e));
    }
    return null;
  }

}
