package org.oasp.module.monitoring.jmx.test;

import org.junit.Assert;
import org.junit.Test;
import org.oasp.module.monitoring.service.impl.jmx.ServiceStatisticsMBean;

/**
 * Test case for {@link ServiceStatisticsMBean}.
 * 
 * @author mvielsac
 */
public class ServiceStatisticsMBeanTest extends Assert {

  private ServiceStatisticsMBean serviceStatistics = new ServiceStatisticsMBean();

  /**
   * Tests the average calculation for a full buffer.
   */
  @Test
  public void avarageCalculationTest() {

    int buffersize = 10;
    long[] execTimes = { 100, 150, 200, 250, 111, 222, 333, 123, 231, 321 };
    long sum = 0;

    for (int i = 0; i < execTimes.length; i++) {
      sum += execTimes[i];
      this.serviceStatistics.countExecution(execTimes[i], true);
    }

    assertEquals(new Long(sum / buffersize), this.serviceStatistics.getAvarageExecTime());

  }

  /**
   * Tests the average calculation for a buffer which is not full.
   */
  @Test
  public void avarageCalculationUnderfullBufferTest() {

    int buffersize = 4;
    long[] execTimes = { 100, 150, 200, 250 };
    long sum = 0;

    for (int i = 0; i < execTimes.length; i++) {
      sum += execTimes[i];
      this.serviceStatistics.countExecution(execTimes[i], true);
    }

    assertEquals(new Long(sum / buffersize), this.serviceStatistics.getAvarageExecTime());

  }

  /**
   * Test the average calculation for a empty buffer.
   */
  @Test
  public void avargeCalculationEmptyBuffer() {

    assertEquals(new Long(0), this.serviceStatistics.getAvarageExecTime());

  }
}
