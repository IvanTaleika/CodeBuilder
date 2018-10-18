package cb.core.editors.designEditor.method;

import java.util.Map;
import cb.core.editors.designEditor.node.MethodNode;

public interface IMethod {

  String getAccess();

  void setAccess(String access);

  String getReturnType();

  void setReturnType(String returnType);

  String getName();

  void setName(String name);

  Map<String, String> getVariablesMap();

  void addVariable(String name, String type);

  void deleteVariable(String name, String type);

  Map<String, String> getPassedVariablesMap();
  
  MethodNode getBeginNode();
  
  String getAsCode();
  
  String getValuesAsCode();
}
