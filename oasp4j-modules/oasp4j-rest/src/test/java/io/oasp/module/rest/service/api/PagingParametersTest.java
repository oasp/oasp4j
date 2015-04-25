package io.oasp.module.rest.service.api;

import net.sf.mmm.util.search.base.AbstractSearchCriteria;

import org.junit.Assert;
import org.junit.Test;

/**
 * This type is the test-case of {@link PagingParameters}
 *
 * @author llaszkie
 */
public class PagingParametersTest extends Assert {

  private PagingParameters parameters;

  /**
   * Test method for {@link io.oasp.module.rest.service.api.PagingParameters#isOff()}.
   */
  @Test
  public void testIsOff() {

    // given // when
    this.parameters = new PagingParameters();

    // then
    assertTrue("Paging support is off.", this.parameters.isOff());
  }

  /**
   * Test method for {@link io.oasp.module.rest.service.api.PagingParameters#isOff()}.
   */
  @Test
  public void testIsOn() {

    // given // when
    this.parameters = new PagingParameters(1, null);

    // then
    assertFalse("Paging support is on.", this.parameters.isOff());
  }

  /**
   * Test method for
   * {@link io.oasp.module.rest.service.api.PagingParameters#extendIfRequested(net.sf.mmm.util.search.base.AbstractSearchCriteria)}
   * .
   */
  @Test
  @SuppressWarnings("serial")
  public void testExtend() {

    // given
    this.parameters = new PagingParameters(2, 5);
    AbstractSearchCriteria criteria = new AbstractSearchCriteria() {
    };

    // when
    this.parameters.extendIfRequested(criteria);

    // then
    assertEquals("Search criteria offset was set.", 1 * 5, criteria.getHitOffset());
    assertEquals("Search criteria maximum hit count was set.", 5, criteria.getMaximumHitCount().intValue());
  }

}
