package by.bsuir.cb.design.code.node;

import java.util.HashMap;

public class FunctionNode extends MethodNode {
  private MethodNode nextNode;

  public FunctionNode(String codeTemplate, HashMap<String, String> keywordValueMap) {
    super(codeTemplate, keywordValueMap, MethodNode.FUNCTION);
  }

  public MethodNode getNext() {
    return nextNode;
  }

  public void addNext(MethodNode methodNode) {
    if (nextNode != null) {
      switch (methodNode.getType()) {
        case MethodNode.CONDITION:
          ((ConditionNode) methodNode).addNext(nextNode);
          break;
        case MethodNode.FUNCTION:
          ((FunctionNode) methodNode).addNext(nextNode);
          break;
      }
      nextNode.removePrevious(this);
    }
    nextNode = methodNode;
    methodNode.addPrevious(this);
  }

  public MethodNode removeNext() {
    MethodNode returnNode = nextNode;
    nextNode.removePrevious(this);
    nextNode = null;
    return returnNode;
  }

}
