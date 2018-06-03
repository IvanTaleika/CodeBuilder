package cb.core.editors.designEditor.node;

import java.util.HashMap;
import java.util.LinkedList;
import cb.core.code.utils.CodeUtilsProvider;

public class ConditionNode extends MethodNode {

  public ConditionNode(LinkedList<String> conditionsTemplates,
      LinkedList<HashMap<String, String>> conditionsMaps, LinkedList<String> conditionsStyles) {
    super(null, null, CodeUtilsProvider.CONDITION);
    for (int i = 0; i < conditionsTemplates.size(); i++) {
      super.addNext(NodeFactory.create(conditionsTemplates.get(i), conditionsMaps.get(i),
          conditionsStyles.get(i)));
    }
  }

  // TODO check algorithm
  @Override
  public void addNext(IMethodNode methodNode) {
    for (IMethodNode childNode : getNextNodes()) {
      IMethodNode lastNode = getLastNode(childNode.getNextNodes());
      if (lastNode == null) {
        childNode.addNext(methodNode);
      } else {
        lastNode.addNext(methodNode);
      }
    }
  }

  private IMethodNode getLastNode(LinkedList<IMethodNode> methodNodes) {
    for (IMethodNode methodNode : methodNodes) {
      if (methodNode.getNextNodes().isEmpty() || methodNode.getType() == CodeUtilsProvider.CONDITION) {
        return methodNode;
      }
      return getLastNode(methodNode.getNextNodes());
    }
    return null;
  }


}
