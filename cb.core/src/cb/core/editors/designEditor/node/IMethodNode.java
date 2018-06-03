package cb.core.editors.designEditor.node;

import java.util.HashMap;
import java.util.LinkedList;

public interface IMethodNode {
  public LinkedList<IMethodNode> getNextNodes();

  public LinkedList<IMethodNode> getPreviousNodes();

  public void addNext(IMethodNode methodNode);

  public void addPrevious(IMethodNode methodNode);

  public void removePrevious(IMethodNode methodNode);

  public void removeNext(IMethodNode methodNode);

  public String getCodeTemplate();

  public HashMap<String, String> getKeywordValueMap();

  public String getType();
}
