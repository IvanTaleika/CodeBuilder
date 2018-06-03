package cb.core.editors.designEditor.node;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class MethodNode implements IMethodNode {
  private final String TYPE;
  private final LinkedList<IMethodNode> nextNodes;
  private final LinkedList<IMethodNode> previousNodes;
  private String codeTemplate;
  private HashMap<String, String> keywordValueMap;

  {
    nextNodes = new LinkedList<>();
    previousNodes = new LinkedList<>();
  }

  public MethodNode(String codeTemplate, HashMap<String, String> keywordValueMap, String type) {
    this.codeTemplate = codeTemplate;
    this.keywordValueMap = keywordValueMap;
    TYPE = type;
  }

  public LinkedList<IMethodNode> getNextNodes() {
    return nextNodes;
  }

  public LinkedList<IMethodNode> getPreviousNodes() {
    return previousNodes;
  }

  public void addNext(IMethodNode methodNode) {
    nextNodes.add(methodNode);
  }

  public void addPrevious(IMethodNode methodNode) {
    previousNodes.add(methodNode);
  }

  public void removePrevious(IMethodNode methodNode) {
    previousNodes.remove(methodNode);
  }

  public void removeNext(IMethodNode methodNode) {
    nextNodes.remove(methodNode);
  }
  
  public String getCodeTemplate() {
    return codeTemplate;
  }

  public HashMap<String, String> getKeywordValueMap() {
    return keywordValueMap;
  }

  public String getType() {
    return TYPE;
  }




}
