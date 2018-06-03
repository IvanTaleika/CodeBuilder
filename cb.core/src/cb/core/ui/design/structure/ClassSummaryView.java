package cb.core.ui.design.structure;

import java.util.HashMap;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import cb.core.CodeBuilder;
import cb.core.ui.design.IDesignViewPart;
import cb.core.ui.design.structure.dialogs.AddMethodDialog;
import cb.core.ui.design.structure.dialogs.AddValueDialog;

public class ClassSummaryView implements IDesignViewPart {
  private final String ADD_IMAGE = "add.png";
  private final String DELETE_IMAGE = "delete.png";
  private AddMethodDialog addMethodDialog;
  private AddValueDialog addValueDialog;
  private Composite uiParent;
  private ViewForm summaryViewForm;
  private Table methodsTable;
  private Table variablesTable;

  // TODO add tabs here
  public ClassSummaryView(Composite uiParent) {
    this.uiParent = uiParent;

  }



  @Override
  public ViewForm getGUI() {
    return summaryViewForm;
  }

  @Override
  public void buildGUI() {


    summaryViewForm = new ViewForm(uiParent, SWT.NONE);

    CTabFolder summaryTabFolder = new CTabFolder(summaryViewForm, SWT.BORDER);
    summaryViewForm.setContent(summaryTabFolder);

    CTabItem methodsCTabItem = new CTabItem(summaryTabFolder, SWT.NONE);
    methodsCTabItem.setText(StructureViewMessages.ClassSummaryView_1stTabName);
    methodsTable = new Table(summaryTabFolder, SWT.FULL_SELECTION);
    methodsCTabItem.setControl(methodsTable);



    CTabItem valuesTabItem = new CTabItem(summaryTabFolder, SWT.NONE);
    valuesTabItem.setText(StructureViewMessages.ClassSummaryView_2ndTabName);
    variablesTable = new Table(summaryTabFolder, SWT.FULL_SELECTION);
    valuesTabItem.setControl(variablesTable);

    CLabel viewFormTitle = new CLabel(summaryViewForm, SWT.NONE);
    // TODO set image
    summaryViewForm.setTopLeft(viewFormTitle);
    viewFormTitle.setText(StructureViewMessages.ClassSummaryView_ViewTitle);
    viewFormTitle.setToolTipText(StructureViewMessages.ClassSummaryView_ViewToolTip);

    ToolBar summaryViewFormToolBar = new ToolBar(summaryViewForm, SWT.FLAT | SWT.RIGHT);
    summaryViewForm.setTopRight(summaryViewFormToolBar);

    ToolItem addItem = new ToolItem(summaryViewFormToolBar, SWT.NONE);
    addItem.setImage(CodeBuilder.getImage(ADD_IMAGE));
    addItem.setToolTipText(StructureViewMessages.ClassSummaryView_AddToolTip);
    addItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (summaryTabFolder.getSelection() == methodsCTabItem) {
          addMethod();
        } else {
          addValue();
        }
      }
    });

    ToolItem deleteItem = new ToolItem(summaryViewFormToolBar, SWT.NONE);
    deleteItem.setImage(CodeBuilder.getImage(DELETE_IMAGE));
    deleteItem.setToolTipText(StructureViewMessages.ClassSummaryView_DeleteToolTip);
    deleteItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (summaryTabFolder.getSelection() == methodsCTabItem) {
          deleteMethod();
        } else {
          deleteVariable();
        }
      }
    });

    summaryTabFolder.setSelection(methodsCTabItem);
  }

  private void addMethod() {
    if (addMethodDialog == null) {
      addMethodDialog = new AddMethodDialog(uiParent.getShell());
    }
    int result = addMethodDialog.open();
    if (result == AddMethodDialog.OK) {
      String access = addMethodDialog.getAccess();
      String returnType = addMethodDialog.getReturnType();
      String name = addMethodDialog.getName();
      String methodString = access.trim() + " " + returnType.trim() + " " + name.trim() + "(";
      String variables = addMethodDialog.getVariables();
      if (!variables.isEmpty()) {
        String[] variablesArray = variables.split(",");
        HashMap<String, String> typeVariableMap = new HashMap<>();
        for (String variable : variablesArray) {
          variable.trim();
          String[] temp = variable.split("\\s");
          if (temp.length == 2) {
            typeVariableMap.put(temp[0], temp[1]);
            methodString += temp[0] + ", ";
          }
        }
      }
      methodString.trim();
      methodString += ")";
      // TODO add images not access modifiers
      TableItem method = new TableItem(methodsTable, SWT.NONE);
      method.setText(methodString);
      // TODO send this info to editor
    }

  }

  private void addValue() {
    if (addValueDialog == null) {
      addValueDialog = new AddValueDialog(uiParent.getShell());
    }
    int result = addValueDialog.open();
    if (result == AddValueDialog.OK) {
      String access = addValueDialog.getAccess();
      String type = addValueDialog.getType();
      String name = addValueDialog.getName();
      // TODO add images not access modifiers
      
      String valueString = access.trim() + " " + name.trim() + " : " + type.trim();
      TableItem value = new TableItem(variablesTable, SWT.NONE);
      value.setText(valueString);
      // TODO send this info to editor
    }
  }

  private void deleteMethod() {
    int index = methodsTable.getSelectionIndex();
    if(index != -1) {
      methodsTable.getItem(index).dispose();
      // TODO send this info to editor 
    }
  }

  private void deleteVariable() {
    int index = variablesTable.getSelectionIndex();
    if(index != -1) {
      variablesTable.getItem(index).dispose();
      // TODO send this info to editor 
    }
  }

  @Override
  public void setParent(Composite parent) {
    uiParent = parent;

  }

  @Override
  public Composite getParent() {
    return uiParent;
  }
}
