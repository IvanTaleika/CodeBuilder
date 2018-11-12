package by.bsuir.cb.design.code.method;

import java.util.HashMap;

public interface IMethodListener {
  void methodCreated(String access, String returnType, String name,
      HashMap<String, String> passedVariables);

  void methodDeleted(int methodIndex);

  // TODO add initialization
  void valueCreated(int methodIndex, String name, String type);

  void valueDeleted(int methodIndex, String name, String type);

  void methodSwitched(int methodIndex);

}
