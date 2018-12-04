package by.bsuir.cb.design.ui.outline;

import org.eclipse.jdt.core.IJavaElement;

public interface IOutlineSelectionListener {

  /**
   * Called when selection in {@link DesignEditorOutlinePage} changed.
   *
   * @param selection selected IJavaElement if any, {@code null} otherwise
   */
  void selectionChanged(IJavaElement selection);
}
