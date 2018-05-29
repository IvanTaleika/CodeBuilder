package cb.core.ui.design.operations.components.factories;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import cb.core.ui.design.operations.components.OperationButton;
import cb.core.ui.design.operations.components.OperationWidget;

//TODO add interface for OperationFactory
public class OperationButtonFactory implements IOperationWidgetFactory {

  //TODO add xml Node as an argument
  public static OperationWidget create(Composite parent) {
    //TODO style from xml???
    OperationButton operationButton = new OperationButton(parent, SWT.FLAT | SWT.TOGGLE);
    Button button = (Button) operationButton.getUI();
    //TODO check GridData
    button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    
    //TODO text from xml
    button.setText("My button");
    
    //FIXME move this to separate class
//    IWorkspace workspace = ResourcesPlugin.getWorkspace();
//    IPath path = workspace.getRoot().getFullPath();
//    String stringPath = path.toString();
//    IProject project = workspace.getRoot().getProject();
//    IFolder images =  project.getFolder("images/operationsController");
//    IFolder test =  project.getFolder("images");
//    test = test.getFolder("operationsController");
//    IFile file = images.getFile("begin.gif");
//    
//    path = file.getFullPath();
//    stringPath = path.toOSString();
    //TODO convert to image
    return operationButton;
  }

}
