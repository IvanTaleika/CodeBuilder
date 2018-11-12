package by.bsuir.cb.design.ui.structure;

import org.eclipse.osgi.util.NLS;

public class StructureViewMessages extends NLS {
  private static final String BASE_NAME =
      "by.bsuir.cb.design.ui.structure.structureMessages"; //$NON-NLS-1$
  public static String MethodTree_ViewTitle;
  public static String MethodTree_ViewTooltip;
  public static String MethodTree_ExpandAllToolTip;
  public static String MethodTree_CollapseAllToolTip;
  public static String MethodTree_AdditingToEndTitle;
  public static String MethodTree_AdditingToEndMessage;
  public static String ClassSummary_ViewTitle;
  public static String ClassSummary_ViewToolTip;
  public static String ClassSummary_1stTabName;
  public static String ClassSummary_2ndTabName;
  public static String ClassSummary_AddToolTip;
  public static String ClassSummary_DeleteToolTip;

  static {
    NLS.initializeMessages(BASE_NAME, StructureViewMessages.class);
  }

  private StructureViewMessages() {

  }
}
