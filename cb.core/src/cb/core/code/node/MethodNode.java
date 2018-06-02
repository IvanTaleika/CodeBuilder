package cb.core.code.node;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class MethodNode {
  private final String TYPE;
  private final LinkedList<MethodNode> nextNodes;
  private final LinkedList<MethodNode> previousNodes;
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

  public LinkedList<MethodNode> getNextNodes() {
    return nextNodes;
  }

  public LinkedList<MethodNode> getPreviousNodes() {
    return previousNodes;
  }

  public void addNext(MethodNode methodNode) {
    nextNodes.add(methodNode);
  }

  public void addPrevious(MethodNode methodNode) {
    previousNodes.add(methodNode);
  }

  public void removePrevious(MethodNode methodNode) {
    previousNodes.remove(methodNode);
  }

  public void removeNext(MethodNode methodNode) {
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
