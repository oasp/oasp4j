package io.oasp.module.logging.common.api;

/**
 * Central constants for logging.
 *
 * @author rstroh
 */
public final class LoggingConstants {

  /**
   * The key for the correlation id used as unique identifier to correlate log entries of a processing task. It allows
   * to track down all related log messages for that task across the entire application landscape (e.g. in case of a
   * problem).
   *
   * @see DiagnosticContextFacade#setCorrelationId(String)
   */
  public static final String CORRELATION_ID = "correlationId";

  /**
   * Construction prohibited.
   */
  private LoggingConstants() {

    super();
  }

}
