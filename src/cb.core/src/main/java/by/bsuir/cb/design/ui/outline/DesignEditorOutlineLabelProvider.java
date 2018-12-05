package by.bsuir.cb.design.ui.outline;

import by.bsuir.cb.BundleResourceProvider;
import by.bsuir.cb.CodeBuilder;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class DesignEditorOutlineLabelProvider implements ILabelProvider {
  private static final ILog LOGGER = CodeBuilder.getDefault().getLog();
  private ASTParser parser;
  private IJavaProject target;
  public static final String TYPE_ICON_PATH = "outline/class_obj.gif";
  public static final String METHOD_PRIVATE_PATH = "outline/methpri_obj.gif";
  public static final String METHOD_PROTECTED_PATH = "outline/methpro_obj.gif";
  public static final String METHOD_PUBLIC_PATH = "outline/methpub_obj.gif";
  public static final String FIELD_PRIVATE_PATH = "outline/field_private_obj.gif";
  public static final String FIELD_PROTECTED_PATH = "outline/field_protected_obj.gif";
  public static final String FIELD_PUBLIC_PATH = "outline/field_public_obj.gif";

  public DesignEditorOutlineLabelProvider(IJavaProject target) {
    parser = ASTParser.newParser(AST.JLS10);
    this.target = target;
  }

  public void setTargetProject(IJavaProject target) {
    this.target = target;
  }

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
      return BundleResourceProvider.getImage(getMethodImagePath((IMethod) element));
    } else if (element instanceof IField) {
      return BundleResourceProvider.getImage(getFieldImagePath((IField) element));
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

  private String getFieldImagePath(IField field) {
    parser.setProject(target);
    var binding = parser.createBindings(new IField[] {field}, null)[0];
    if (binding != null) {
      int modifiers = binding.getModifiers();
      if (Modifier.isPrivate(modifiers)) {
        return FIELD_PRIVATE_PATH;
      } else if (Modifier.isPublic(modifiers)) {
        return FIELD_PUBLIC_PATH;
      }
      return FIELD_PROTECTED_PATH;
    } else {
      return null;
    }
  }

  private String getMethodImagePath(IMethod method) {
    parser.setProject(target);
    var binding = parser.createBindings(new IMethod[] {method}, null)[0];
    if (binding != null) {
      int modifiers = binding.getModifiers();
      if (Modifier.isPrivate(modifiers)) {
        return METHOD_PRIVATE_PATH;
      } else if (Modifier.isPublic(modifiers)) {
        return METHOD_PUBLIC_PATH;
      }
      return METHOD_PROTECTED_PATH;
    } else {
      return null;
    }
  }
}
