package by.bsuir.cb.design.code.node;

import java.util.HashMap;

public class ReturnNode extends MethodNode {
  public ReturnNode(String codeTemplate, HashMap<String, String> keywordValueMap) {
    super(codeTemplate, keywordValueMap, MethodNode.RETURN);
  }
}
