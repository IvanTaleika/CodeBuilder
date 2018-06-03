package cb.core.editors.designEditor.node;

import java.util.HashMap;
import cb.core.code.utils.CodeUtilsProvider;

public class ReturnNode extends MethodNode{
  public ReturnNode(String codeTemplate, HashMap<String, String> keywordValueMap) {
    super(codeTemplate, keywordValueMap, CodeUtilsProvider.RETURN);
  }
}
