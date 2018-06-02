package cb.core.code.node;

import java.util.HashMap;
import cb.core.code.codeBuilder.CodeBuilder;

public class ReturnNode extends MethodNode{
  public ReturnNode(String codeTemplate, HashMap<String, String> keywordValueMap) {
    super(codeTemplate, keywordValueMap, CodeBuilder.RETURN);
  }
}
