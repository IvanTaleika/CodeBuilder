package by.bsuir.cb.design;

import by.bsuir.cb.CodeBuilder;
import by.bsuir.cb.design.code.ICodeGenerator;
import by.bsuir.cb.design.code.JavaCodeGenerator;
import by.bsuir.cb.design.code.method.IMethod;
import by.bsuir.cb.design.code.method.IMethodListener;
import by.bsuir.cb.design.code.method.Method;
import by.bsuir.cb.design.code.node.ConditionNode;
import by.bsuir.cb.design.code.node.FunctionNode;
import by.bsuir.cb.design.code.node.MethodNode;
import by.bsuir.cb.design.ui.BundleResourceProvider;
import by.bsuir.cb.design.ui.operations.OperationPicker;
import by.bsuir.cb.design.ui.structure.ClassSummary;
import by.bsuir.cb.design.ui.structure.MethodTree;
import by.bsuir.cb.utils.PathProvider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.IScanner;
import org.eclipse.jdt.core.compiler.ITerminalSymbols;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jdt.ui.IWorkingCopyManager;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

// TODO add save possibility
// This class should use JavaCodeGenerator and write result into the file
public class DesignEditor extends EditorPart implements IMethodListener {
  private static final String GENERATE_IMAGE = "generate.png";
  private static final int[] SASH_FORM_WEIGHT = {120, 150, 250};

  private List<MethodNode> visitedNodes;
  private List<IMethod> methods;
  private IMethod currentMethod;
  private ICompilationUnit compilationUnit;
  private ICodeGenerator codeGenerator;
  // TODO add interface for all method views
  // TODO this is not view - rename!
  private OperationPicker operationPicker;
  private MethodTree methodTree;
  private ClassSummary classSummary;

  private IWorkbenchPartSite partSite;


  @Override
  public void doSave(IProgressMonitor monitor) {
    // TODO Auto-generated method stub

  }

  @Override
  public void doSaveAs() {
    // TODO Auto-generated method stub

  }

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    IWorkingCopyManager workingCopyManager = JavaUI.getWorkingCopyManager();
    compilationUnit = workingCopyManager.getWorkingCopy(input);
    methods = new LinkedList<>();
    partSite = site;
    // TODO May be there might be the way to use DI
    codeGenerator = new JavaCodeGenerator();
  }

  @Override
  public boolean isDirty() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isSaveAsAllowed() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setFocus() {
    // TODO Auto-generated method stub
  }

  private void createToolbar(ViewForm parent) {
    ToolBar mainViewFormToolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
    parent.setTopLeft(mainViewFormToolBar);
    ToolItem addItem = new ToolItem(mainViewFormToolBar, SWT.NONE);
    addItem.setImage(CodeBuilder.getImage(GENERATE_IMAGE));
    addItem.setToolTipText(DesignPageMessages.GenerateButton_ToolTip);
    addItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        if (currentMethod != null) {
          addMethod();
        }
      }
    });
  }

  @Override
  public void createPartControl(Composite parent) {
    try {
      parent.setLayout(new FillLayout());
      ViewForm mainViewForm = new ViewForm(parent, SWT.NONE);
      createToolbar(mainViewForm);



      SashForm shellSashForm = new SashForm(mainViewForm, SWT.BORDER | SWT.SMOOTH);
      mainViewForm.setContent(shellSashForm);

      Group structureGroup = new Group(shellSashForm, SWT.NONE);
      structureGroup.setText(DesignPageMessages.Structure_title);
      structureGroup.setLayout(new FillLayout());

      SashForm structureSashForm = new SashForm(structureGroup, SWT.BORDER | SWT.VERTICAL);

      Composite dummy = new Composite(structureSashForm, SWT.BORDER);
      dummy.setLayout(new FillLayout());
      Label dummyLabel = new Label(dummy, SWT.NONE);
      dummyLabel.setText("Coming soon");


      classSummary = new ClassSummary(structureSashForm);
      classSummary.buildGui();
      classSummary.addMethodListener(this);
      File templateFile;
      try {
        templateFile = BundleResourceProvider.getFile(PathProvider.getTemplateClasspath());
      } catch (IOException e) {
        throw new CbResourceException("Unable to load operations template File");
      }

      operationPicker = new OperationPicker(shellSashForm, templateFile);
      operationPicker.buildGui();

      methodTree = new MethodTree(shellSashForm);
      methodTree.buildGui();
      operationPicker.addOperationsListener(methodTree);

      shellSashForm.setWeights(SASH_FORM_WEIGHT);

    } catch (Exception e) {
      for (Control control : parent.getChildren()) {
        if (!control.isDisposed()) {
          control.dispose();
        }
      }
      // TODO move exception messages to specific file
      Label errorLabel = new Label(parent, SWT.NONE);
      errorLabel.setText("Plugin resources error:\n" + e.getMessage());
    }
  }

  private void addMethod() {
    try {
      IBuffer buffer = compilationUnit.getBuffer();
      ISourceRange range = compilationUnit.getSourceRange();
      int start = range.getOffset();
      int length = range.getLength();
      IScanner scanner;
      scanner = ToolFactory.createScanner(false, false, false, false);
      scanner.setSource(buffer.getCharacters());
      scanner.resetTo(start, start + length - 1);
      int token;
      int pos = 0;
      while ((token = scanner.getNextToken()) != ITerminalSymbols.TokenNameEOF) {
        if (token == ITerminalSymbols.TokenNameRBRACE) {
          pos = scanner.getCurrentTokenStartPosition();
        }
      }
      String code = generateCode();
      code = formateCode(code);
      buffer.replace(pos, 0, code);
    } catch (JavaModelException exception) {
      exception.printStackTrace();
    } catch (CbGenerationException exception) {
      // TODO open error for all exceptions
      MessageDialog.openWarning(getSite().getShell(), "CodeBuilder error",
          "Error while generated data: \n" + exception.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String generateCode() throws CbGenerationException {
    visitedNodes = new LinkedList<>();
    StringBuffer methodCode =
        recursiveCodeGeneration(currentMethod.getBeginNode(), new StringBuffer());
    methodCode.append("}\n");
    methodCode.insert(0, currentMethod.getValuesAsCode());
    methodCode.insert(0, currentMethod.getAsCode());
    return methodCode.toString();
  }

  @SuppressWarnings("unchecked")
  private String formateCode(String source) {
    @SuppressWarnings("rawtypes")
    Map options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();

    options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_10);
    options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_10);
    options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_10);

    // change the option to wrap each enum constant on a new line
    options.put(DefaultCodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ENUM_CONSTANTS,
        DefaultCodeFormatterConstants.createAlignmentValue(true,
            DefaultCodeFormatterConstants.WRAP_ONE_PER_LINE,
            DefaultCodeFormatterConstants.INDENT_ON_COLUMN));

    // instantiate the default code formatter with the given options
    final CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(options);

    final TextEdit edit = codeFormatter.format(CodeFormatter.K_CLASS_BODY_DECLARATIONS, // format a
        source, // source to format
        0, // starting position
        source.length(), // length
        0, // initial indentation
        System.getProperty("line.separator") // line separator
    );
    if (edit == null) {
      return source;
    }

    IDocument document = new Document(source);
    try {
      edit.apply(document);
    } catch (MalformedTreeException | BadLocationException e) {
      e.printStackTrace();
    }
    return document.get();
  }


  private StringBuffer recursiveCodeGeneration(MethodNode node, StringBuffer code)
      throws CbGenerationException {
    if (visitedNodes.contains(node)) {
      return code.insert(0, "}");
    }
    visitedNodes.add(node);
    try {
      switch (node.getType()) {
        case MethodNode.CONDITION:
          ConditionNode conditionNode = (ConditionNode) node;
          List<MethodNode> nextNodes = conditionNode.getNextNodes();
          if (nextNodes.isEmpty()) {
            throw new CbGenerationException(node.getType() + " no return node");
          }
          for (int i = nextNodes.size() - 1; i >= 0; i--) {
            recursiveCodeGeneration(nextNodes.get(i), code);
          }
          break;
        case MethodNode.FUNCTION:
          FunctionNode functionNode = (FunctionNode) node;
          MethodNode nextNode = functionNode.getNext();
          if (nextNode == null) {
            throw new CbGenerationException(node.getType() + " no return node");
          }
          recursiveCodeGeneration(nextNode, code);
          break;
        case MethodNode.RETURN:
          code.insert(0, codeGenerator.generate(node.getCodeTemplate(), node.getKeywordValueMap()));
          if (node.getPreviousNodes().size() > 1) {
            code.insert(0, "}");
          }
          return code;
      }
    } catch (CbGenerationException exception) {
      throw new CbGenerationException(node.getType() + " - " + exception.getMessage());
    }
    if (node.getPreviousNodes().size() > 1) {
      code.insert(0, "}");
    }
    return code.insert(0,
        codeGenerator.generate(node.getCodeTemplate(), node.getKeywordValueMap()));
  }


  @Override
  public void methodCreated(String access, String returnType, String name,
      HashMap<String, String> passedVariables) {
    currentMethod = new Method(access, returnType, name, passedVariables);
    methods.add(currentMethod);
    methodTree.addMethod(currentMethod);
  }

  @Override
  public void methodDeleted(int methodIndex) {
    methodTree.deleteMethod(methods.remove(methodIndex));
  }

  @Override
  public void valueCreated(int methodIndex, String name, String type) {
    currentMethod.addVariable(name, type);
  }

  @Override
  public void valueDeleted(int methodIndex, String name, String type) {
    currentMethod.deleteVariable(name, type);
  }

  @Override
  public void methodSwitched(int methodIndex) {
    currentMethod = methods.get(methodIndex);
    methodTree.switchMethod(methodIndex);
  }

  // TODO what is this? Note: this solve error with nullpointer exception
  @Override
  public IWorkbenchPartSite getSite() {
    return partSite;
  }


}
