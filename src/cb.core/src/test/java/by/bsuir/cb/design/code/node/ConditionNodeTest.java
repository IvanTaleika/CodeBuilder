package by.bsuir.cb.design.code.node;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;
import by.bsuir.cb.design.code.IGenerative;
import by.bsuir.cb.design.code.Keyword;
import by.bsuir.cb.design.ui.method.TreeViewDecorator;

public class ConditionNodeTest {
  public static final String TEST_TEMPLATE =
      "template ${condition1} - ${result1}, {condition2} - ${result2}";
  public static final String[] KEYWORDS = {"condition1", "result1", "condition2", "result2"};
  public ConditionNode testNode;


  /**
   * Tests that {@link ConditionNode} will keep the keyword in template if no value is specified
   */
  @Test
  public void toCodeStringDefaultKeywordsTest() {



    testNode = new ConditionNode(null);
    testNode.setTemplate(TEST_TEMPLATE);
    Map<String, IGenerative> keywordMap = new HashMap<>();

    var condition1 = new Keyword();
    condition1.setType("a");
    condition1.setValue("lol");
    var condition2 = new Keyword();
    condition2.setType("b");
    var conditionResult1 = new Keyword();
    conditionResult1.setType("conditionResult");
    var conditionResult2 = new Keyword();
    conditionResult2.setType("conditionResult");
    keywordMap.put("condition1", condition1);
    keywordMap.put("condition2", condition2);
    keywordMap.put("result1", conditionResult1);
    keywordMap.put("result2", conditionResult2);
    testNode.setKeywordMap(keywordMap);
    int index = 1;
    for (Entry<String, IGenerative> entry : testNode.getKeywordMap()
        .entrySet()) {
      if (((Keyword) entry.getValue()).getType().equals("conditionResult")) {
        var decorator = new TreeViewDecorator();
        decorator.setIconPath("operations/begin_end.gif");
        decorator.setName("Begin" + index);
        index++;
        var beginNode = new BeginNode(testNode);
        decorator.setSource(beginNode);
        testNode.appendChild(decorator);
        entry.setValue(beginNode);
      }
    }
    testNode.toCodeString();
  }

  /**
   * Tests that {@link ConditionNode} will invoke keyword's associated {@link Keyword}'s
   * {@code toString} method.
   */
  @Test
  public void toCodeStringSpecifiedKeywordsTest() {}
}
