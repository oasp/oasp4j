package org.oasp.module.monitoring.service.impl.jmx;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * MBean for service statistics.
 * 
 * @author mvielsac
 */
@ManagedResource(description = "Returns information about the service.")
public class ServiceStatisticsMBean implements MethodInterceptor {

  private static final int MILLIS_PER_MINUTE = 60000;

  private static final int NUM_OF_EXECS_FOR_AVARAGE = 10;

  private volatile int numOfCallsLastMinute;

  private volatile int numOfErrorsLastMinute;

  private volatile int numOfCallsThisMinute;

  private volatile int numOfErrorsThisMinute;

  private volatile int lastMinute;

  private volatile CircularFifoQueue<Long> execTimes = new CircularFifoQueue<>(NUM_OF_EXECS_FOR_AVARAGE);

  /**
   * {@inheritDoc}
   */
  public Object invoke(MethodInvocation invocation) throws Throwable {

    long startTime = System.currentTimeMillis();
    boolean success = false;

    try {
      Object result = invocation.proceed();
      success = true;
      return result;
    } catch (Exception e) {
      // TODO check which exceptions are no errors and set success = true;
      throw e;
    } finally {
      long execTime = System.currentTimeMillis() - startTime;
      countExecution(execTime, success);
      // TODO add log statement "Method X executed un/successfully in xyz ms.
    }

  }

  public synchronized void countExecution(Long duration, Boolean success) {

    updateTimeWindow();
    ++this.numOfCallsThisMinute;
    if (!success) {
      ++this.numOfErrorsThisMinute;
    }

    this.execTimes.add(duration);

  }

  private synchronized void updateTimeWindow() {

    // get current minute
    int currentMinute = (int) (System.currentTimeMillis() / MILLIS_PER_MINUTE);

    if (currentMinute != this.lastMinute) {
      this.lastMinute = currentMinute;
      this.numOfCallsLastMinute = this.numOfCallsThisMinute;
      this.numOfErrorsLastMinute = this.numOfErrorsThisMinute;

      this.numOfCallsThisMinute = 0;
      this.numOfErrorsThisMinute = 0;
    }
  }

  @ManagedAttribute(description = "Returns the avarage execution time of the last 10 calls in millis.")
  public Long getAvarageExecTime() {

    long result = 0;

    if (this.execTimes.size() > 0) {
      // copy to array to avoid concurrent changes
      Long[] execTimesArray = this.execTimes.toArray(new Long[this.execTimes.size()]);

      long sum = 0;

      for (Long execTime : execTimesArray) {
        sum += execTime.longValue();
      }

      result = sum / execTimesArray.length;
    }

    return Long.valueOf(result);

  }

  @ManagedAttribute(description = "Returns the number of calls without error in the last minute")
  public int getNumOfCallsLastMinute() {

    updateTimeWindow();
    return this.numOfCallsLastMinute;
  }

  @ManagedAttribute(description = "Returns the number of errors occured in the last minute")
  public int getNumOfErrosLastMinute() {

    updateTimeWindow();
    return this.numOfErrorsLastMinute;
  }
}
