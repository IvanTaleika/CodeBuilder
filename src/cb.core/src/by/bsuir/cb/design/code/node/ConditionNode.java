package by.bsuir.cb.design.code.node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ConditionNode extends MethodNode {
  private List<MethodNode> nextNodes;


  public ConditionNode(String codeTemplate, HashMap<String, String> keywordValueMap) {
    super(codeTemplate, keywordValueMap, MethodNode.CONDITION);
    nextNodes = new LinkedList<>();
  }

  public List<MethodNode> getNextNodes() {
    return nextNodes;
  }

  public void addBranch(MethodNode methodNode) {
    nextNodes.add(methodNode);
    methodNode.addPrevious(this);
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
