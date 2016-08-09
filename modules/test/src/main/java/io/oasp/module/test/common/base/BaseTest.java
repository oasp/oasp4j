package io.oasp.module.test.common.base;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since 2.2.0
 */
public class BaseTest extends Assertions {
  @Before
  public final void setUp() {

    doSetUp();
  }

  @After
  public final void tearDown() {

    doTearDown();
  }

  protected void doSetUp() {

  }

  protected void doTearDown() {

  }
}
