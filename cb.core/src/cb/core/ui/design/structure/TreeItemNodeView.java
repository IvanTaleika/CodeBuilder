package cb.core.ui.design.structure;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.swt.widgets.TreeItem;
import cb.core.editors.designEditor.node.MethodNode;
import cb.core.ui.design.structure.dialogs.CustomizeTemplateDialog;

public class TreeItemNodeView {
  private List<TreeItem> nodeTreeItems;
  private CustomizeTemplateDialog customizeTemplateDialog;
  private final MethodNode node;
  
  public TreeItemNodeView(MethodNode node) {
    this.node = node;
    nodeTreeItems = new LinkedList<>();
  }

  public MethodNode getNode() {
    return node;
  }
  
  public List<TreeItem> getNodeTreeItems() {
    return nodeTreeItems;
  }
  
  public boolean addNodeTreeItem(TreeItem treeItem) {
    return nodeTreeItems.add(treeItem);
  }
  
  public boolean removeNodeTreeItem(TreeItem treeItem) {
    return nodeTreeItems.remove(treeItem);
  }
  
  //TODO this work weird 
  public boolean containsNodeTreeItem(TreeItem treeItem) {
    for (TreeItem item : nodeTreeItems) {
      if(item == treeItem) {
        return true;
      }
    }
    return false;
  }
  

  public CustomizeTemplateDialog getCustomizeNodeDialog() {
    return customizeTemplateDialog;
  }

  public void setCustomizeNodeDialog(CustomizeTemplateDialog customizeTemplateDialog) {
    this.customizeTemplateDialog = customizeTemplateDialog;
  }
}
