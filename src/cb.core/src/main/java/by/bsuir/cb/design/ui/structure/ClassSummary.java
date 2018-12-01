package by.bsuir.cb.design.ui.structure;

import by.bsuir.cb.CodeBuilder;
import by.bsuir.cb.design.code.method.IMethodListener;
import by.bsuir.cb.design.ui.structure.dialogs.AddMethodDialog;
import by.bsuir.cb.design.ui.structure.dialogs.AddValueDialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class ClassSummary {

  private static final String ADD_IMAGE = "add.png";
  private static final String DELETE_IMAGE = "delete.png";
  private static final HashMap<String, String> ACCESS_SYMBOLS_MAP = new HashMap<>();

  {
    ACCESS_SYMBOLS_MAP.put("public", "+");
    ACCESS_SYMBOLS_MAP.put("protected", "#");
    ACCESS_SYMBOLS_MAP.put("private", "-");
  }

  private AddMethodDialog addMethodDialog;
  private AddValueDialog addValueDialog;
  private Composite parent;

  private Composite valueTabComposite;
  private StackLayout valueTabCompositeLayout;
  private LinkedList<Table> valueTables;

  private ViewForm summaryViewForm;
  private Table methodsTable;
  private Table currentValuesTable;

  private LinkedList<IMethodListener> methodListeners;

  public ClassSummary(Composite parent) {
    this.parent = parent;
    methodListeners = new LinkedList<>();
  }

  public ViewForm getGui() {
    return summaryViewForm;
  }

  public void buildGui() {
    valueTables = new LinkedList<>();

    summaryViewForm = new ViewForm(parent, SWT.NONE);

    CTabFolder summaryTabFolder = new CTabFolder(summaryViewForm, SWT.BORDER);
    summaryViewForm.setContent(summaryTabFolder);

    CTabItem methodsCTabItem = new CTabItem(summaryTabFolder, SWT.NONE);
    methodsCTabItem.setText(StructureViewMessages.ClassSummary_1stTabName);
    methodsTable = new Table(summaryTabFolder, SWT.FULL_SELECTION);
    methodsCTabItem.setControl(methodsTable);
    // TODO this listener works even if u didn't click the method item, but chose it
    methodsTable.addListener(SWT.MouseDoubleClick, new Listener() {
      @Override
      public void handleEvent(Event event) {
        switchMethod();
      }
    });



    CTabItem valuesTabItem = new CTabItem(summaryTabFolder, SWT.NONE);
    valuesTabItem.setText(StructureViewMessages.ClassSummary_2ndTabName);
    valueTabComposite = new Composite(summaryTabFolder, SWT.NONE);
    valueTabCompositeLayout = new StackLayout();
    valueTabComposite.setLayout(valueTabCompositeLayout);
    valuesTabItem.setControl(valueTabComposite);

    CLabel viewFormTitle = new CLabel(summaryViewForm, SWT.NONE);
    // TODO set image
    summaryViewForm.setTopLeft(viewFormTitle);
    viewFormTitle.setText(StructureViewMessages.ClassSummary_ViewTitle);
    viewFormTitle.setToolTipText(StructureViewMessages.ClassSummary_ViewToolTip);

    ToolBar summaryViewFormToolBar = new ToolBar(summaryViewForm, SWT.FLAT | SWT.RIGHT);
    summaryViewForm.setTopRight(summaryViewFormToolBar);

    ToolItem addItem = new ToolItem(summaryViewFormToolBar, SWT.NONE);
    addItem.setImage(CodeBuilder.getImage(ADD_IMAGE));
    addItem.setToolTipText(StructureViewMessages.ClassSummary_AddToolTip);
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
    deleteItem.setToolTipText(StructureViewMessages.ClassSummary_DeleteToolTip);
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


  public void setParent(Composite parent) {
    this.parent = parent;

  }

  public Composite getParent() {
    return parent;
  }

  public void addMethodListener(IMethodListener listener) {
    methodListeners.add(listener);
  }

  public void removeMethodListener(IMethodListener listener) {
    methodListeners.remove(listener);
  }

  private void switchValueTable(Table table) {
    currentValuesTable = table;
    valueTabCompositeLayout.topControl = table;
    valueTabComposite.layout();
  }


  private void addMethod() {
    if (addMethodDialog == null) {
      addMethodDialog = new AddMethodDialog(parent.getShell());
    }
    int result = addMethodDialog.open();
    if (result == AddMethodDialog.OK) {
      String access = addMethodDialog.getAccess().trim();
      String returnType = addMethodDialog.getReturnType().trim();
      String name = addMethodDialog.getName().trim();
      String passedVariables = addMethodDialog.getVariables();

      HashMap<String, String> passedVariablesMap = new HashMap<>();
      if (!passedVariables.isEmpty()) {
        String[] variablesArray = passedVariables.split(",");
        for (String variable : variablesArray) {
          variable = variable.trim();
          String[] temp = variable.split("\\s");
          if (temp.length == 2) {
            passedVariablesMap.put(temp[1], temp[0]);
          }
        }
      }
      addMethodToView(access, returnType, name, passedVariablesMap);
      for (IMethodListener methodListener : methodListeners) {
        methodListener.methodCreated(access, returnType, name, passedVariablesMap);
      }
    }
  }

  private void addMethodToView(String access, String returnType, String name,
      HashMap<String, String> passedVariablesMap) {
    String methodString = ACCESS_SYMBOLS_MAP.get(access) + " " + name + "(";


    for (Entry<String, String> value : passedVariablesMap.entrySet()) {
      methodString += value.getValue() + " " + value.getKey() + ", ";
    }

    if (methodString.matches(".*(, )$")) {
      methodString = methodString.substring(0, methodString.length() - 2);
    }
    methodString += ") : " + returnType;
    TableItem method = new TableItem(methodsTable, SWT.NONE);
    method.setText(methodString);
    methodsTable.setSelection(method);
    valueTables.add(new Table(valueTabComposite, SWT.NONE));
    switchValueTable(valueTables.get(methodsTable.getSelectionIndex()));

    if (!passedVariablesMap.isEmpty()) {
      for (Entry<String, String> passedVariable : passedVariablesMap.entrySet()) {
        addValueToView(passedVariable.getKey(), passedVariable.getValue());
      }
    }
  }

  private void addValue() {
    // TODO protect from duplication variables
    if (methodsTable.getItemCount() == 0) {
      return;
    }
    if (addValueDialog == null) {
      addValueDialog = new AddValueDialog(parent.getShell());
    }
    int result = addValueDialog.open();
    if (result == AddValueDialog.OK) {
      String type = addValueDialog.getType();
      String name = addValueDialog.getName();
      addValueToView(name, type);

      for (IMethodListener methodListener : methodListeners) {
        methodListener.valueCreated(valueTables.indexOf(currentValuesTable), name, type);
      }
    }
  }

  private void addValueToView(String name, String type) {
    String valueString = name + " : " + type;
    TableItem value = new TableItem(currentValuesTable, SWT.NONE);
    value.setText(valueString);
  }

  private void deleteMethod() {
    int index = methodsTable.getSelectionIndex();
    if (index != -1) {
      Table table = valueTables.remove(index);
      if (currentValuesTable == table) {
        switchValueTable(null);
      }
      methodsTable.getItem(index).dispose();

      for (IMethodListener methodListener : methodListeners) {
        methodListener.methodDeleted(index);
      }
    }
  }

  private void deleteVariable() {
    int index = currentValuesTable.getSelectionIndex();
    if (index != -1) {
      TableItem deletedItem = currentValuesTable.getItem(index);
      String[] nameAndType = deletedItem.getText().split(" : ");
      deletedItem.dispose();
      for (IMethodListener methodListener : methodListeners) {
        methodListener.valueDeleted(valueTables.indexOf(currentValuesTable), nameAndType[0],
            nameAndType[1]);
      }
    }
  }

  private void switchMethod() {
    int methodIndex = methodsTable.getSelectionIndex();
    if (methodIndex != -1) {
      switchValueTable(valueTables.get(methodIndex));
      for (IMethodListener methodListener : methodListeners) {
        methodListener.methodSwitched(methodIndex);
      }
    }
  }
}
