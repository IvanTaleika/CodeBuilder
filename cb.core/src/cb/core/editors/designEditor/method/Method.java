package cb.core.editors.designEditor.method;

import java.util.HashMap;
import java.util.Map;
import cb.core.editors.designEditor.node.MethodNode;
import cb.core.editors.designEditor.node.NodeFactory;

public class Method implements IMethod {
  private MethodNode beginNode;
  private String access;
  private String returnType;
  private String name;
  private Map<String, String> variablesMap;
  private Map<String, String> passedVariables;
  
  public Method(String access, String returnType, String name, HashMap<String, String> passedVariables) {
    this.access = access;
    this.name = name;
    this.passedVariables = passedVariables;
    variablesMap = new HashMap<>();
    variablesMap.putAll(passedVariables);
    beginNode = NodeFactory.create(null, null, MethodNode.FUNCTION);
    
  }

  public String getAccess() {
    return access;
  }

  public void setAccess(String access) {
    this.access = access;
  }

  public String getReturnType() {
    return returnType;
  }

  public void setReturnType(String returnType) {
    this.returnType = returnType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addVariable(String name, String type) {
    variablesMap.put(name, type);
  }

  @Override
  public void deleteVariable(String name, String type) {
    variablesMap.remove(name);
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


}