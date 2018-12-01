package by.bsuir.cb.design.code.method;

import by.bsuir.cb.design.code.node.MethodNode;

import java.util.Map;

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
