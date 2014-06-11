package org.oasp.module.monitoring.jmx;

import java.util.Calendar;
import java.util.Date;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * MBean for monitoring the application status.
 * 
 * @author mvielsac
 */
@ManagedResource(description = "Returns the status of the application.")
public class StatusMonitorMBean {

  private boolean lastCheckSuccessful;

  private Date timeOfLastCheck;

  /**
   * Registers a check with the result of the check and updates the {@link #getTimeOfLastCheck() timeOfLastCheck}
   * attribute.
   * 
   * @param success result of system check
   */
  public void registerCheck(boolean success) {

    this.lastCheckSuccessful = success;
    this.timeOfLastCheck = Calendar.getInstance().getTime();
  }

  /**
   * Returns the field 'lastCheckSuccessful'.
   * 
   * @return value of lastCheckSuccessful
   */
  @ManagedAttribute(description = "Returns true if the last check was successful or false if the last check failed.")
  public boolean isLastCheckSuccessful() {

    return this.lastCheckSuccessful;
  }

  /**
   * Returns the field 'timeOfLastCheck'.
   * 
   * @return value of timeOfLastCheck
   */
  @ManagedAttribute(description = "Time of the last check.")
  public Date getTimeOfLastCheck() {

    return this.timeOfLastCheck;
  }

}
