package by.bsuir.cb.design.code.node;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import by.bsuir.cb.design.code.IGenerative;
import by.bsuir.cb.design.code.Keyword;

public class TemplateNodeTest {
  public static final String TEST_TEMPLATE = "template ${node} ${test} string";
  public static final String[] KEYWORDS = {"node", "test"};
  public TemplateNode testNode;

  @Before
  public void initNode() {
    testNode = new TemplateNode(null);
    testNode.setTemplate(TEST_TEMPLATE);
    Map<String, IGenerative> keywordMap = new HashMap<>();
    for (String keyword : KEYWORDS) {
      keywordMap.put(keyword, new Keyword());
    }
    testNode.setKeywordMap(keywordMap);
  }

  /**
   * Tests that {@link TemplateNode} will keep the keyword in template if no value is specified
   */
  @Test
  public void toCodeStringDefaultKeywordsTest() {
    Assert.assertEquals(TEST_TEMPLATE, testNode.toCodeString());
  }

  /**
   * Tests that {@link TemplateNode} will invoke keyword's associated {@link Keyword}'s
   * {@code toString} method.
   */
  @Test
  public void toCodeStringSpecifiedKeywordsTest() {
    var map = testNode.getKeywordMap();
    map.values().forEach(v -> ((Keyword) v).setValue(RandomStringUtils.random(10)));
    Assert.assertEquals(
        String.format("template %s %s string",
            map.values().stream().map(v -> v.toCodeString()).toArray()),
        testNode.toCodeString());
  }
}
