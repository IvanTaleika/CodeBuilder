package by.bsuir.cb.design.ui.utils;

import by.bsuir.cb.CodeBuilder;
import java.util.Optional;
import org.eclipse.core.runtime.Status;
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
   * Opens dialog for workspace's scope Java types.
   *
   * @return optional with type if it was chosen by user, empty optional otherwise.
   */
  public static Optional<IType> openJavaTypeDialog(Shell shell) {
    try {
      SelectionDialog dialog = JavaUI.createTypeDialog(shell, new ProgressMonitorDialog(shell),
          SearchEngine.createWorkspaceScope(), IJavaElementSearchConstants.CONSIDER_ALL_TYPES,
          false);
      dialog.setTitle(DialogMessages.TypeDialog_Title);
      dialog.setMessage(DialogMessages.TypeDialog_Message);

      if (dialog.open() == Window.OK) {
        IType type = (IType) dialog.getResult()[0];
        return Optional.of(type);
      }
    } catch (JavaModelException e) {
      CodeBuilder.getDefault().getLog()
          .log(new Status(Status.ERROR, CodeBuilder.PLUGIN_ID, e.getMessage(), e));
    }
    return Optional.empty();

  }

}
