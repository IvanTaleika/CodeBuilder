package cb.core.editors.designEditor.node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import cb.core.code.utils.CodeUtilsProvider;
import cb.core.exceptions.CBResourceException;
import cb.core.utils.XMLParseUtils;

// FIXME add condition nodes
public class NodeFactory {
  private static final String TEMPLATE_TAG = "template";
  private static final String KEYWORD_TAG = "keywords";
  private static final String NODE_TYPE_ATTRIBUTE = "type";
  private static final String KEYWORD_DEFAULT_ATTRIBUTE = "default";
  private static final String TEMPLATE_CODE_ATTRIBUTE = "code";

  public static MethodNode create(Element nodeData) throws CBResourceException {
    String type = null;
    String codeTemplate = null;
    HashMap<String, String> keywordValueMap = new HashMap<>();
    try {
      type = nodeData.getAttribute(NODE_TYPE_ATTRIBUTE);

      Element template = XMLParseUtils.getElements(nodeData.getElementsByTagName(TEMPLATE_TAG)).getFirst();

      codeTemplate = template.getAttribute(TEMPLATE_CODE_ATTRIBUTE);
      // TODO check for good looking text
      HashSet<String> templateKeywords =
          CodeUtilsProvider.getTemplateParser().getKeywords(codeTemplate);

      Element keywords = XMLParseUtils.getElements(nodeData.getElementsByTagName(KEYWORD_TAG)).getFirst();

      HashMap<String, String> keyValueMap = new HashMap<>();
      for (String keyword : templateKeywords) {
        try {

          Element defaultValue = XMLParseUtils.getElements(keywords.getElementsByTagName(keyword)).getFirst();
          keyValueMap.put(keyword, defaultValue.getAttribute(KEYWORD_DEFAULT_ATTRIBUTE));
          // TODO getToolTip

        } catch (Exception e) {
          keyValueMap.put(keyword, "");
        }
      }
    } catch (Exception e) {
      throw new CBResourceException("Node data error.", e);
    }
    return create(codeTemplate, keywordValueMap, type);
  }


  public static MethodNode create(String codeTemplate, HashMap<String, String> keywordValueMap,
      String type) {
    switch (type) {
      case CodeUtilsProvider.FUNCTION:
        return new FunctionNode(codeTemplate, keywordValueMap);
      case CodeUtilsProvider.RETURN:
        return new ReturnNode(codeTemplate, keywordValueMap);
      default:
        return null;
    }
  }

  public static MethodNode create(MethodNode methodNode) {
    switch (methodNode.getType()) {
      case CodeUtilsProvider.FUNCTION:
        return new FunctionNode(methodNode.getCodeTemplate(), methodNode.getKeywordValueMap());
      case CodeUtilsProvider.RETURN:
        return new ReturnNode(methodNode.getCodeTemplate(), methodNode.getKeywordValueMap());
      default:
        return null;
    }
  }



}
