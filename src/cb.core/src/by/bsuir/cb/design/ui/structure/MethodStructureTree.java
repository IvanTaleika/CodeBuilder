package by.bsuir.cb.design.ui.structure;

import by.bsuir.cb.CodeBuilder;
import by.bsuir.cb.design.code.method.IMethod;
import by.bsuir.cb.design.code.node.ConditionNode;
import by.bsuir.cb.design.code.node.FunctionNode;
import by.bsuir.cb.design.code.node.MethodNode;
import by.bsuir.cb.design.code.node.NodeFactory;
import by.bsuir.cb.design.ui.operation.IOperation;
import by.bsuir.cb.design.ui.operation.IOperationListener;
import by.bsuir.cb.design.ui.structure.dialogs.CustomizeTemplateDialog;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

// TODO use TreeView
public class MethodStructureTree implements IOperationListener {
  private IOperation selectedOperation;

  public static final String BEGIN_ITEM_IMAGE = "operations/icons/begin_end.gif";
  public static final String EXPAND_ALL_IMAGE = "expand_all.gif";
  public static final String COLLAPSE_ALL_IMAGE = "collapse_all.gif";
  public static final String ADD_NODE_CURSOR_IMAGE = "add_cursor.gif";

  private Composite parent;
  private ViewForm treeViewForm;
  private Composite treeComposite;
  private StackLayout treeCompositeLayout;
  private Map.Entry<IMethod, Tree> currentTree;
  private List<TreeItemNodeView> currentTreeNodes;
  private List<Map.Entry<IMethod, Tree>> methodTrees;
  private Map<Map.Entry<IMethod, Tree>, List<TreeItemNodeView>> treeNodesMap;
  private Cursor addNodeCursor;
  private Cursor arrowCursor;

  public MethodStructureTree(Composite parent) {
    this.parent = parent;
    methodTrees = new LinkedList<>();
    treeNodesMap = new HashMap<>();
  }


  public ViewForm getGui() {
    return treeViewForm;
  }

  public void buildGui() {
    treeViewForm = new ViewForm(parent, SWT.NONE);
    createCursors();


    treeComposite = new Composite(treeViewForm, SWT.NONE);
    treeViewForm.setContent(treeComposite);
    treeCompositeLayout = new StackLayout();
    treeComposite.setLayout(treeCompositeLayout);

    CLabel viewFormTitle = new CLabel(treeViewForm, SWT.NONE);
    // TODO set image
    treeViewForm.setTopLeft(viewFormTitle);
    viewFormTitle.setText(StructureViewMessages.MethodTree_ViewTitle);
    viewFormTitle.setToolTipText(StructureViewMessages.MethodTree_ViewTooltip);

    ToolBar treeViewFormToolBar = new ToolBar(treeViewForm, SWT.FLAT | SWT.RIGHT);
    treeViewForm.setTopRight(treeViewFormToolBar);

    ToolItem expandTreeItem = new ToolItem(treeViewFormToolBar, SWT.NONE);
    expandTreeItem.setImage(CodeBuilder.getImage(EXPAND_ALL_IMAGE));
    expandTreeItem.setToolTipText(StructureViewMessages.MethodTree_ExpandAllToolTip);
    expandTreeItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        expandCurrentTree();
      }
    });

    ToolItem collapseTreeItem = new ToolItem(treeViewFormToolBar, SWT.NONE);
    collapseTreeItem.setImage(CodeBuilder.getImage(COLLAPSE_ALL_IMAGE));
    collapseTreeItem.setToolTipText(StructureViewMessages.MethodTree_CollapseAllToolTip);
    collapseTreeItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        collapseCurrentTree();
      }
    });
  }



  public void addMethod(IMethod method) {
    Tree tree = new Tree(treeComposite, SWT.NONE);
    addTreeListeners(tree);

    Map.Entry<IMethod, Tree> methodTree = new AbstractMap.SimpleEntry<IMethod, Tree>(method, tree);
    methodTrees.add(methodTree);
    TreeItemNodeView beginItem = new TreeItemNodeView(method.getBeginNode());
    TreeItem beginTreeItem = new TreeItem(tree, SWT.NONE);
    beginTreeItem.setText(method.getName());
    beginTreeItem.setImage(CodeBuilder.getImage(BEGIN_ITEM_IMAGE));
    beginItem.addNodeTreeItem(beginTreeItem);
    List<TreeItemNodeView> treeNodes = new LinkedList<>();
    treeNodes.add(beginItem);
    currentTree = methodTree;
    treeNodesMap.put(methodTree, treeNodes);
    currentTreeNodes = treeNodes;
    treeCompositeLayout.topControl = tree;
    treeComposite.layout();
  }

  public void switchMethod(int methodIndex) {
    currentTree = methodTrees.get(methodIndex);
    currentTreeNodes = treeNodesMap.get(currentTree);
    treeCompositeLayout.topControl = currentTree.getValue();
    treeComposite.layout();
  }

  public void deleteMethod(IMethod method) {
    Map.Entry<IMethod, Tree> deletedTree = null;
    for (Map.Entry<IMethod, Tree> methodTree : methodTrees) {
      if (methodTree.getKey() == method) {
        deletedTree = methodTree;
        treeNodesMap.remove(methodTree);
        methodTrees.remove(methodTree);
      }
    }
    if (currentTree == deletedTree) {
      currentTree = null;
      currentTreeNodes = null;
      treeCompositeLayout.topControl = null;
      treeComposite.layout();
    }

  }


  public void setParent(Composite parent) {
    this.parent = parent;

  }

  public Composite getParent() {
    return parent;
  }

  @Override
  public void operationSelected(IOperation operation) {
    if (selectedOperation == operation) {
      selectedOperation = null;
      return;
    }
    if (selectedOperation != null) {
      selectedOperation.setSelection(false);
    }
    selectedOperation = operation;
  }


  private void expandCurrentTree() {
    if (currentTree != null) {
      for (TreeItem treeItem : currentTree.getValue().getItems()) {
        treeItem.setExpanded(true);
      }
    }
  }

  private void collapseCurrentTree() {
    if (currentTree != null) {
      for (TreeItem treeItem : currentTree.getValue().getItems()) {
        treeItem.setExpanded(false);
      }
    }
  }

  // FIXME move children to new parent
  private void addNewNode(TreeItem selectedItem) {
    for (TreeItemNodeView treeItemNodeView : currentTreeNodes) {
      if (treeItemNodeView.containsNodeTreeItem(selectedItem)) {
        MethodNode selectedNode = treeItemNodeView.getNode();
        switch (selectedNode.getType()) {
          case MethodNode.CONDITION: {
            MethodNode newNode = NodeFactory.create((selectedOperation.getNode()));

            TreeItemNodeView newNodeView = new TreeItemNodeView(newNode);

            List<MethodNode> parentNodes = ((ConditionNode) selectedNode).addNext(newNode);
            for (MethodNode methodNode : parentNodes) {
              for (TreeItemNodeView nodeView : currentTreeNodes) {
                if (nodeView.getNode() == methodNode) {
                  for (TreeItem parent : nodeView.getNodeTreeItems()) {
                    TreeItem nodeTreeItem = new TreeItem(parent, SWT.NONE);
                    parent.setExpanded(true);
                    nodeTreeItem.setText(selectedOperation.getName());
                    nodeTreeItem.setImage(selectedOperation.getIcon());
                    newNodeView.addNodeTreeItem(nodeTreeItem);
                  }
                }
              }
            }
            if (newNode.getType() == MethodNode.CONDITION) {
              buildIf((ConditionNode) newNode, newNodeView);

            }

            currentTreeNodes.add(newNodeView);
            break;
          }
          case MethodNode.FUNCTION:
            MethodNode newNode = NodeFactory.create((selectedOperation.getNode()));
            TreeItemNodeView newNodeView = new TreeItemNodeView(newNode);
            ((FunctionNode) selectedNode).addNext(newNode);
            for (TreeItem parent : treeItemNodeView.getNodeTreeItems()) {
              TreeItem nodeTreeItem = new TreeItem(parent, SWT.NONE);
              parent.setExpanded(true);
              nodeTreeItem.setText(selectedOperation.getName());
              nodeTreeItem.setImage(selectedOperation.getIcon());
              newNodeView.addNodeTreeItem(nodeTreeItem);
            }
            currentTreeNodes.add(newNodeView);
            if (newNode.getType() == MethodNode.CONDITION) {
              buildIf((ConditionNode) newNode, newNodeView);

            }

            break;
          case MethodNode.RETURN:
            MessageDialog.openWarning(parent.getShell(),
                StructureViewMessages.MethodTree_AdditingToEndTitle,
                StructureViewMessages.MethodTree_AdditingToEndMessage);
            break;
        }
        break;
      }
    }
  }

  private void customizeSelection(TreeItem selectedItem) {
    for (TreeItemNodeView treeItemNodeView : currentTreeNodes) {
      if (treeItemNodeView.containsNodeTreeItem(selectedItem)) {
        if (treeItemNodeView.getNode().getKeywordValueMap() != null) {
          CustomizeTemplateDialog dialog = treeItemNodeView.getCustomizeNodeDialog();
          if (dialog == null) {
            dialog = new CustomizeTemplateDialog(parent.getShell());
            dialog.setNodeKeywordValueMap(treeItemNodeView.getNode().getKeywordValueMap());
            treeItemNodeView.setCustomizeNodeDialog(dialog);
          }
          dialog.setMethodVariables(currentTree.getKey().getVariablesMap());
          dialog.open();
        }
        break;
      }

    }
  }

  private void createCursors() {
    addNodeCursor = new Cursor(Display.getDefault(),
        CodeBuilder.getImage(ADD_NODE_CURSOR_IMAGE).getImageData(), 0, 0);

    arrowCursor = parent.getCursor();
    treeViewForm.addDisposeListener(new DisposeListener() {
      @Override
      public void widgetDisposed(DisposeEvent e) {
        addNodeCursor.dispose();
      }
    });
  }

  private void addTreeListeners(Tree tree) {
    tree.addListener(SWT.MouseDoubleClick, new Listener() {
      @Override
      public void handleEvent(Event event) {
        TreeItem[] selection = currentTree.getValue().getSelection();
        if (selection != null) {
          customizeSelection(selection[0]);
        }
      }

    });
    tree.addMouseTrackListener(new MouseTrackAdapter() {
      @Override
      public void mouseEnter(MouseEvent e) {
        if (selectedOperation != null) {
          tree.setCursor(addNodeCursor);
        }
      }

      @Override
      public void mouseExit(MouseEvent e) {
        if (selectedOperation != null) {
          tree.setCursor(arrowCursor);
        }
      }
    });
    tree.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        TreeItem[] selection = currentTree.getValue().getSelection();
        if (selectedOperation != null && selection != null) {
          addNewNode(selection[0]);
        }
      }

    });
    // TODO add delete operation
  }

  // TODO delete
  private void buildIf(ConditionNode node, TreeItemNodeView conditionNodeView) {
    List<MethodNode> nextNodes = node.getNextNodes();
    TreeItemNodeView trueNodeView = new TreeItemNodeView(nextNodes.get(0));
    for (TreeItem parent : conditionNodeView.getNodeTreeItems()) {
      TreeItem nodeTreeItem = new TreeItem(parent, SWT.NONE);
      parent.setExpanded(true);
      nodeTreeItem.setText("true");
      nodeTreeItem.setImage(selectedOperation.getIcon());
      trueNodeView.addNodeTreeItem(nodeTreeItem);
    }
    currentTreeNodes.add(trueNodeView);
    TreeItemNodeView falseNodeView = new TreeItemNodeView(nextNodes.get(1));
    for (TreeItem parent : conditionNodeView.getNodeTreeItems()) {
      TreeItem nodeTreeItem = new TreeItem(parent, SWT.NONE);
      parent.setExpanded(true);
      nodeTreeItem.setText("false");
      nodeTreeItem.setImage(selectedOperation.getIcon());
      falseNodeView.addNodeTreeItem(nodeTreeItem);
    }
    currentTreeNodes.add(falseNodeView);
  }

}
