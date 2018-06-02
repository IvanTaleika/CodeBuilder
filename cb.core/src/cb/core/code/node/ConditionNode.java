package cb.core.code.node;

import java.util.HashMap;
import java.util.LinkedList;
import cb.core.code.codeBuilder.CodeBuilder;

public class ConditionNode extends MethodNode {

  public ConditionNode(LinkedList<String> conditionsTemplates,
      LinkedList<HashMap<String, String>> conditionsMaps, LinkedList<String> conditionsStyles) {
    super(null, null, CodeBuilder.CONDITION);
    for (int i = 0; i < conditionsTemplates.size(); i++) {
      super.addNext(NodeFactory.create(conditionsTemplates.get(i), conditionsMaps.get(i),
          conditionsStyles.get(i)));
    }
  }

  // TODO check algorithm
  @Override
  public void addNext(MethodNode methodNode) {
    for (MethodNode childNode : getNextNodes()) {
      MethodNode lastNode = getLastNode(childNode.getNextNodes());
      if (lastNode == null) {
        childNode.addNext(methodNode);
      } else {
        lastNode.addNext(methodNode);
      }
    }
  }

  private MethodNode getLastNode(LinkedList<MethodNode> methodNodes) {
    for (MethodNode methodNode : methodNodes) {
      if (methodNode.getNextNodes().isEmpty() || methodNode.getType() == CodeBuilder.CONDITION) {
        return methodNode;
      }
      return getLastNode(methodNode.getNextNodes());
    }
    return null;
  }


}
