package by.bsuir.cb.design.code.node;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import by.bsuir.cb.design.code.Keyword;

public class EndNodeTest {

  public EndNode testNode;

  @Before
  public void initNode() {
    testNode = new EndNode(null);
  }

  /**
   * Tests that {@link EndNode} will generate "return null;" if no keyword value is specified
   */
  @Test
  public void toCodeStringDefaultKeywordsTest() {
    Assert.assertEquals("return null;", testNode.toCodeString());
  }

  /**
   * Tests that {@link EndNode} will invoke keyword's associated {@link Keyword}'s {@code toString}
   * method.
   */
  @Test
  public void toCodeStringSpecifiedKeywordsTest() {
    var randomValue = RandomStringUtils.random(10);
    var testKeyword = new Keyword();
    testKeyword.setValue(randomValue);
    testNode.getKeywordMap().put("returnValue", testKeyword);
    Assert.assertEquals(String.format("return %s;", randomValue), testNode.toCodeString());
  }
}
