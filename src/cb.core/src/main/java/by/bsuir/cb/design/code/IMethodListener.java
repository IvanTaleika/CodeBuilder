package by.bsuir.cb.design.code;

import org.eclipse.jdt.core.IMethod;

public interface IMethodListener {
  void methodSelected(IMethod method);

  void methodDeleted(IMethod method);

}
