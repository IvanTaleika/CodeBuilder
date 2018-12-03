package by.bsuir.cb.design.code.method;

import java.util.HashMap;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

public interface IMethodListener {
  void methodCreated(String access, String returnType, String name,
      HashMap<String, String> passedVariables);

  void methodDeleted(int methodIndex);

  // TODO add initialization
  void valueCreated(int methodIndex, String name, String type);

  void valueDeleted(int methodIndex, String name, String type);

  void methodSwitched(int methodIndex);

  void init(IEditorSite site, IEditorInput input) throws PartInitException;

}
