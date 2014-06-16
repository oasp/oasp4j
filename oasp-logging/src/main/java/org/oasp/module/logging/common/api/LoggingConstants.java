package org.oasp.module.logging.common.api;

/**
 * Central constants for logging.
 * 
 * @author rstroh
 */
public final class LoggingConstants {

  /** The key for correlation id used to correlate log entries of a process or request. */
  public static final String CORRELATION_ID = "correlationId";

  /**
   * Construction prohibited.
   */
  private LoggingConstants() {

    super();
  }

}
