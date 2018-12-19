package by.bsuir.cb.design.code;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KeywordTest {
  public Keyword testKeyword;

  @Before
  public void initNode() {
    testKeyword = new Keyword();
  }

  /**
   * Tests that {@link Keyword} will return an empty String if no value is specified
   */
  @Test
  public void toCodeStringNoValueTest() {
    Assert.assertTrue(testKeyword.toCodeString().isEmpty());
  }

  /**
   * Tests that {@link Keyword} will return value if it's specified
   */
  @Test
  public void toCodeStringVelueSpecifiedTest() {
    var randomValue = RandomStringUtils.random(10);
    testKeyword.setValue(randomValue);
    Assert.assertEquals(randomValue, testKeyword.toCodeString());
  }
}
