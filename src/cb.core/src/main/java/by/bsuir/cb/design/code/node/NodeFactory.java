package by.bsuir.cb.design.code.node;

import by.bsuir.cb.design.CbResourceException;
import by.bsuir.cb.utils.XMLParseUtils;

import java.util.HashMap;
import java.util.Set;

import org.w3c.dom.Element;

// FIXME FIX CONDITION IMPORT
// TODO return null, not throws exceptions
public class NodeFactory {
  private static final String TEMPLATE_TAG = "template";
  private static final String KEYWORD_TAG = "keywords";
  private static final String NODE_TYPE_ATTRIBUTE = "type";
  private static final String KEYWORD_DEFAULT_ATTRIBUTE = "default";
  private static final String TEMPLATE_CODE_ATTRIBUTE = "code";

  public static MethodNode create(Element nodeData) throws CbResourceException {
    String type = null;
    String codeTemplate = null;
    HashMap<String, String> keywordValueMap = new HashMap<>();
    try {
      type = nodeData.getAttribute(NODE_TYPE_ATTRIBUTE);
      if (!type.equals(MethodNode.CONDITION) && !type.equals(MethodNode.FUNCTION)
          && !type.equals(MethodNode.RETURN)) {
        throw new CbResourceException("Unknown node type");
      }
      if (type.equals(MethodNode.CONDITION)) {
        return getIf();
      }

      Element template =
          XMLParseUtils.getElements(nodeData.getElementsByTagName(TEMPLATE_TAG)).getFirst();

      codeTemplate = template.getAttribute(TEMPLATE_CODE_ATTRIBUTE);
      // TODO May be there might be the way to use DI
      Set<String> templateKeywords = new TemplateParser().getKeywords(codeTemplate);

      Element keywords =
          XMLParseUtils.getElements(nodeData.getElementsByTagName(KEYWORD_TAG)).getFirst();

      for (String keyword : templateKeywords) {
        try {

          Element defaultValue =
              XMLParseUtils.getElements(keywords.getElementsByTagName(keyword)).getFirst();
          keywordValueMap.put(keyword, defaultValue.getAttribute(KEYWORD_DEFAULT_ATTRIBUTE));
          // TODO getToolTip for a wizzard

        } catch (Exception e) {
          keywordValueMap.put(keyword, "");
        }
      }
    } catch (Exception e) {
      throw new CbResourceException("Node data error. " + e.getMessage(), e);
    }
    return create(codeTemplate, keywordValueMap, type);
  }


  public static MethodNode create(String codeTemplate, HashMap<String, String> keywordValueMap,
      String type) {
    switch (type) {
      case MethodNode.FUNCTION:
        return new FunctionNode(codeTemplate, keywordValueMap);
      case MethodNode.RETURN:
        return new ReturnNode(codeTemplate, keywordValueMap);
      default:
        return null;
    }
  }

  public static MethodNode create(MethodNode methodNode) {
    switch (methodNode.getType()) {
      case MethodNode.CONDITION:
        return getIf();
      case MethodNode.FUNCTION:
        return new FunctionNode(methodNode.getCodeTemplate(), methodNode.getKeywordValueMap());
      case MethodNode.RETURN:
        return new ReturnNode(methodNode.getCodeTemplate(), methodNode.getKeywordValueMap());
      default:
        return null;
    }
  }

  private static ConditionNode getIf() {
    HashMap<String, String> keywordValueMap = new HashMap<>();
    keywordValueMap.put("condition", "condition");
    ConditionNode node = new ConditionNode("if(${condition}) {", keywordValueMap);
    node.addBranch(new FunctionNode(null, null));
    node.addBranch(new FunctionNode("else {", null));
    return node;
  }

}
