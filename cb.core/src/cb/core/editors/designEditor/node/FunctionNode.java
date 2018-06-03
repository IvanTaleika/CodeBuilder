package cb.core.editors.designEditor.node;

import java.util.HashMap;
import cb.core.code.utils.CodeUtilsProvider;

public class FunctionNode extends MethodNode {

  public FunctionNode(String codeTemplate, HashMap<String, String> keywordValueMap) {
    super(codeTemplate, keywordValueMap, CodeUtilsProvider.FUNCTION);
  }



}
