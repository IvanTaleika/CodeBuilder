package cb.core.code.node;

import java.util.HashMap;
import cb.core.code.codeBuilder.CodeBuilder;

public class FunctionNode extends MethodNode {

  public FunctionNode(String codeTemplate, HashMap<String, String> keywordValueMap) {
    super(codeTemplate, keywordValueMap, CodeBuilder.FUNCTION);
  }

}
