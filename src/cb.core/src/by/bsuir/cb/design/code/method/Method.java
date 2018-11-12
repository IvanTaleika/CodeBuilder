package by.bsuir.cb.design.code.method;

import by.bsuir.cb.design.code.node.MethodNode;
import by.bsuir.cb.design.code.node.NodeFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Method implements IMethod {
  private MethodNode beginNode;
  private String access;
  private String returnType;
  private String name;
  private Map<String, String> variablesMap;
  private Map<String, String> passedVariables;

  public Method(String access, String returnType, String name,
      HashMap<String, String> passedVariables) {
    this.access = access;
    this.name = name;
    this.returnType = returnType;
    this.passedVariables = passedVariables;
    variablesMap = new HashMap<>();
    variablesMap.putAll(passedVariables);
    beginNode = NodeFactory.create(null, null, MethodNode.FUNCTION);

  }

  @Override
  public String getAccess() {
    return access;
  }

  @Override
  public void setAccess(String access) {
    this.access = access;
  }

  @Override
  public String getReturnType() {
    return returnType;
  }

  @Override
  public void setReturnType(String returnType) {
    this.returnType = returnType;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void addVariable(String name, String type) {
    variablesMap.put(name, type);
  }

  @Override
  public void deleteVariable(String name, String type) {
    variablesMap.remove(name);
    passedVariables.remove(name);
  }


  @Override
  public Map<String, String> getVariablesMap() {
    return variablesMap;
  }

  @Override
  public Map<String, String> getPassedVariablesMap() {
    return passedVariables;
  }

  @Override
  public MethodNode getBeginNode() {
    return beginNode;
  }

  @Override
  public String getAsCode() {
    String methodString = access + " " + returnType + " " + name + "(";
    for (Entry<String, String> value : passedVariables.entrySet()) {
      methodString += value.getValue() + " " + value.getKey() + ", ";
    }

    if (methodString.matches(".*(, )$")) {
      methodString = methodString.substring(0, methodString.length() - 2);
    }
    methodString += ") {";
    return methodString;
  }

  @Override
  public String getValuesAsCode() {
    String variablesString = "";
    for (Entry<String, String> value : variablesMap.entrySet()) {
      if (!passedVariables.containsKey(value.getKey())) {
        variablesString += value.getValue() + " " + value.getKey() + ";";
      }
    }
    return variablesString;
  }


}
