package by.bsuir.cb.design.ui.structure.dialogs;

import java.util.Optional;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

public final class DialogUtils {

  private DialogUtils() {}

  /**
   * 
   * @return
   */
  public static Optional<IType> selectJavaType(Shell shell) {
    try {
      SelectionDialog dialog = JavaUI.createTypeDialog(shell, new ProgressMonitorDialog(shell),
          SearchEngine.createWorkspaceScope(), IJavaElementSearchConstants.CONSIDER_ALL_TYPES,
          false);
      dialog.setTitle(DialogsMessages.TypeDialog_Title);
      dialog.setMessage(DialogsMessages.TypeDialog_Message);

      if (dialog.open() == Window.OK) {
        IType type = (IType) dialog.getResult()[0];
        return Optional.of(type);
      }
    } catch (JavaModelException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    return null;

  }

}
