package cb.core.editors.designEditor.node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class MethodNode {
  public final static String CONDITION = "condition";
  public final static String FUNCTION = "function";
  public final static String RETURN = "return";
  
  private final String type;
  //TODO change to Set
  private final LinkedList<MethodNode> previousNodes;
  private String codeTemplate;
  private HashMap<String, String> keywordValueMap;

  {
    previousNodes = new LinkedList<>();
  }

  public MethodNode(String codeTemplate, HashMap<String, String> keywordValueMap, String type) {
    this.codeTemplate = codeTemplate;
    this.keywordValueMap = keywordValueMap;
    this.type = type;
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
