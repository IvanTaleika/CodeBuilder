package by.bsuir.cb.design.ui;

import org.eclipse.swt.layout.GridLayout;

public class GridLayoutFactory {
  public static GridLayout create(int numColumns, boolean makeColumnsEqualWidth, int spacing,
      int margin) {
    GridLayout gridLayout = new GridLayout(numColumns, makeColumnsEqualWidth);
    gridLayout.marginWidth = gridLayout.marginHeight = margin;
    gridLayout.verticalSpacing = gridLayout.horizontalSpacing = spacing;
    return gridLayout;
  }

  public static GridLayout create(int numColumns, boolean makeColumnsEqualWidth) {
    return new GridLayout(numColumns, makeColumnsEqualWidth);
  }

  public static GridLayout create() {
    return new GridLayout();
  }

}
