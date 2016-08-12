package io.oasp.module.test.common.base;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;

/**
 * This is the abstract base class for all tests. In most cases it will be convenient to extend this class.
 *
 * @author shuber
 */
public class BaseTest extends Assertions {

  /**
   * Suggests to use {@link #doSetUp()} method before each tests.
   */
  @Before
  public final void setUp() {

    doSetUp();
  }

  /**
   * Suggests to use {@link #doTearDown()} method before each tests.
   */
  @After
  public final void tearDown() {

    doTearDown();
  }

  /**
   * Provides initialization previous to the creation of the text fixture.
   */
  protected void doSetUp() {

  }

  /**
   * Provides clean up after tests.
   */
  protected void doTearDown() {

  }
}
