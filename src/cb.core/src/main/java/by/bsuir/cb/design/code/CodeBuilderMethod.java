package by.bsuir.cb.design.code;

import by.bsuir.cb.CodeBuilder;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

@Data
public class CodeBuilderMethod implements IGenerative, IScopable {
  private IGenerative parent;
  private static final ILog LOGGER = CodeBuilder.getDefault().getLog();
  private boolean dirty;
  private IMethod javaMethod;
  private List<IGenerative> children = new LinkedList<>();

  @Override
  public String toCodeString() {
    StringBuilder builder = new StringBuilder();
    try {
      builder.append(Signature.toString(javaMethod.getSignature(), javaMethod.getElementName(),
          javaMethod.getParameterNames(), false, true));
      builder.append("{\n");
    } catch (IllegalArgumentException | JavaModelException e) {
      LOGGER.log(new Status(IStatus.ERROR, CodeBuilder.PLUGIN_ID, e.getMessage(), e));
    }
    children.forEach(c -> builder.append(c.toCodeString()));
    return builder.append("\n}").toString();
  }

  @Override
  public void addChild(int index, IGenerative child) {
    children.add(index, child);
  }

  @Override
  public void appendChild(IGenerative child) {
    children.add(child);
  }

  @Override
  public void removeChild(IGenerative child) {
    children.remove(child);
  }

  @Override
  public IGenerative getParent() {
    return null;
  }



}
