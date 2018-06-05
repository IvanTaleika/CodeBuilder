package cb.core.editors.designEditor.node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ConditionNode extends MethodNode {
  private List<MethodNode> nextNodes;


  public ConditionNode(LinkedList<String> conditionsTemplates,
      LinkedList<HashMap<String, String>> conditionsMaps, LinkedList<String> conditionsStyles) {
    super(null, null, MethodNode.CONDITION);
    nextNodes = new LinkedList<>();
    for (int i = 0; i < conditionsTemplates.size(); i++) {
      MethodNode nextNode = NodeFactory.create(conditionsTemplates.get(i), conditionsMaps.get(i),
          conditionsStyles.get(i));
      nextNode.addPrevious(this);
      nextNodes.add(nextNode);
    }
  }

  public List<MethodNode> getNextNodes() {
    return nextNodes;
  }

  public void addBranch(MethodNode methodNode) {
    nextNodes.add(methodNode);
  }
  
  // TODO check algorithm
  public List<MethodNode> addNext(MethodNode methodNode) {
    List<MethodNode> lastNodes = new LinkedList<>();
    for (MethodNode childNode : nextNodes) {
      lastNodes.addAll(addToLastNode(childNode, methodNode));
    }
    return lastNodes;
  }

  private List<MethodNode> addToLastNode(MethodNode destinationNode, MethodNode inputNode) {
    switch (destinationNode.getType()) {
      case MethodNode.CONDITION:
        return ((ConditionNode) destinationNode).addNext(inputNode);
      case MethodNode.FUNCTION:
        MethodNode functionNextNode = ((FunctionNode) destinationNode).getNext();
        if (functionNextNode == null) {
          List<MethodNode> lastNodes = new LinkedList<>();
          lastNodes.add(destinationNode);
          ((FunctionNode) destinationNode).addNext(inputNode);
          return lastNodes;
        } else {
          return addToLastNode(functionNextNode, inputNode);
        }
      case MethodNode.RETURN:
        return new LinkedList<>();
    }
    return null;
  }


}
