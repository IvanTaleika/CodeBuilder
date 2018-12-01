package by.bsuir.cb.design.code;

import by.bsuir.cb.design.code.method.IMethod;
import by.bsuir.cb.design.code.node.ConditionNode;
import by.bsuir.cb.design.code.node.FunctionNode;
import by.bsuir.cb.design.code.node.MethodNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Generator implements IGenerator {
  private List<MethodNode> visitedNodes;

  public Generator() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public String generateCode(IMethod method) throws CbGenerationException {
    visitedNodes = new LinkedList<>();
    StringBuffer methodCode =
        recursiveCodeGeneration(method.getBeginNode(), new StringBuffer());
    methodCode.append("}\n");
    methodCode.insert(0, method.getValuesAsCode());
    methodCode.insert(0, method.getAsCode());
    return methodCode.toString();
  }


  private StringBuffer recursiveCodeGeneration(MethodNode node, StringBuffer code)
      throws CbGenerationException {
    if (visitedNodes.contains(node)) {
      return code.insert(0, "}");
    }
    visitedNodes.add(node);
    try {
      switch (node.getType()) {
        case MethodNode.CONDITION:
          ConditionNode conditionNode = (ConditionNode) node;
          List<MethodNode> nextNodes = conditionNode.getNextNodes();
          if (nextNodes.isEmpty()) {
            throw new CbGenerationException(node.getType() + " no return node");
          }
          for (int i = nextNodes.size() - 1; i >= 0; i--) {
            recursiveCodeGeneration(nextNodes.get(i), code);
          }
          break;
        case MethodNode.FUNCTION:
          FunctionNode functionNode = (FunctionNode) node;
          MethodNode nextNode = functionNode.getNext();
          if (nextNode == null) {
            throw new CbGenerationException(node.getType() + " no return node");
          }
          recursiveCodeGeneration(nextNode, code);
          break;
        case MethodNode.RETURN:
          code.insert(0,
              parametrizeTemplate(node.getCodeTemplate(), node.getKeywordValueMap()));
          if (node.getPreviousNodes().size() > 1) {
            code.insert(0, "}");
          }
          return code;
      }
    } catch (CbGenerationException exception) {
      throw new CbGenerationException(node.getType() + " - " + exception.getMessage());
    }
    if (node.getPreviousNodes().size() > 1) {
      code.insert(0, "}");
    }
    return code.insert(0,
        parametrizeTemplate(node.getCodeTemplate(), node.getKeywordValueMap()));
  }

  // TODO NO HARD CODE
  private String parametrizeTemplate(String template, HashMap<String, String> values) {
    if (template == null) {
      return "";
    } else if (values == null) {
      return template;
    }
    for (Map.Entry<String, String> entry : values.entrySet()) {
      String key = "\\$\\{" + entry.getKey() + "\\}";
      Pattern pattern = Pattern.compile(key);
      Matcher matcher = pattern.matcher(template);
      template = matcher.replaceAll(entry.getValue());

    }
    return template;
  }

}
