package by.bsuir.cb.design.code.node;

import org.junit.Assert;
import org.junit.Test;

public class BeginNodeTest {

  /**
   * Tests that {@link BeginNode} will always return an empty string
   */
  @Test
  public void toCodeStringDefaultKeywordsTest() {
    Assert.assertTrue(new BeginNode(null).toCodeString().isEmpty());
  }

}
