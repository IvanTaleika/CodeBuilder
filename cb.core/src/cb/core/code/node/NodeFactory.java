package cb.core.code.node;

import java.util.HashMap;
import java.util.LinkedList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import cb.core.code.codeBuilder.CodeBuilder;
import cb.core.exceptions.CBResourceException;
//FIXME add condition nodes
public class NodeFactory {
  private static final String TEMPLATE_TYPE = "template";
  private static final String TEMPLATE_TAG = "template";
  private static final String KEYWORD_TAG = "keywords";
  private static final String KEYWORD_DEFAULT_ATTRIBUTE = "default";

  public static MethodNode create(Element nodeData) throws CBResourceException {
    String type = null;
    String codeTemplate = null;
    HashMap<String, String> keywordValueMap = new HashMap<>();
    try {
      type = nodeData.getAttribute(TEMPLATE_TYPE);
      NodeList templatesList = nodeData.getElementsByTagName(TEMPLATE_TAG);

      Element template = null;
      for (int i = 0; i < templatesList.getLength(); i++) {
        if (templatesList.item(i).getNodeType() == Node.ELEMENT_NODE) {
          template = (Element) templatesList.item(i);
        }
      }
      codeTemplate =template.getTextContent().trim();
      LinkedList<String> templateKeywords =
          CodeBuilder.getTemplateParser().getKeywords(codeTemplate);
      NodeList keywordsList = nodeData.getElementsByTagName(KEYWORD_TAG);
      Element keywords = null;
      for (int i = 0; i < keywordsList.getLength(); i++) {
        if (keywordsList.item(i).getNodeType() == Node.ELEMENT_NODE) {
          keywords = (Element) keywordsList.item(i);
        }
      }
      HashMap<String, String> keyValueMap = new HashMap<>();
      for (String keyword : templateKeywords) {
        try {

          NodeList keywordData = keywords.getElementsByTagName(keyword);
          Element defaultValue = null;
          for (int i = 0; i < keywordData.getLength(); i++) {
            if (keywordData.item(i).getNodeType() == Node.ELEMENT_NODE) {
              defaultValue = (Element) keywordData.item(i);
            }
          }
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
      case CodeBuilder.FUNCTION:
        return new FunctionNode(codeTemplate, keywordValueMap);
      case CodeBuilder.RETURN:
        return new ReturnNode(codeTemplate, keywordValueMap);
      default:
        return null;
    }
  }

  public static MethodNode create(MethodNode methodNode) {
    switch (methodNode.getType()) {
      case CodeBuilder.FUNCTION:
        return new FunctionNode(methodNode.getCodeTemplate(), methodNode.getKeywordValueMap());
      case CodeBuilder.RETURN:
        return new ReturnNode(methodNode.getCodeTemplate(), methodNode.getKeywordValueMap());
      default:
        return null;
    }
  }



}
