package by.bsuir.cb.design.code.node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


// TODO rename package
public abstract class MethodNode {
  public static final String CONDITION = "condition";
  public static final String FUNCTION = "function";
  public static final String RETURN = "return";

  private final String type;
  private final LinkedList<MethodNode> previousNodes;
  private String codeTemplate;
  private HashMap<String, String> keywordValueMap;

  public MethodNode(String codeTemplate, HashMap<String, String> keywordValueMap, String type) {
    this.codeTemplate = codeTemplate;
    this.keywordValueMap = keywordValueMap;
    this.type = type;
    previousNodes = new LinkedList<>();
  }

  public List<MethodNode> getPreviousNodes() {
    return previousNodes;
  }

  protected void addPrevious(MethodNode methodNode) {
    previousNodes.add(methodNode);
  }

  protected void removePrevious(MethodNode methodNode) {
    previousNodes.remove(methodNode);

  }

  public String getCodeTemplate() {
    return codeTemplate;
  }

  public HashMap<String, String> getKeywordValueMap() {
    return keywordValueMap;
  }

  public String getType() {
    return type;
  }


}
