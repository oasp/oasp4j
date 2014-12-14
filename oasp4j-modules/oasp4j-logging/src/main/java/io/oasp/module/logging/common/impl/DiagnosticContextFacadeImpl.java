package io.oasp.module.logging.common.impl;

import io.oasp.module.logging.common.api.DiagnosticContextFacade;
import io.oasp.module.logging.common.api.LoggingConstants;

import org.slf4j.MDC;

/**
 * This is the simple and straight forward implementation of {@link DiagnosticContextFacade}.
 *
 * @author hohwille
 */
public class DiagnosticContextFacadeImpl implements DiagnosticContextFacade {

  /**
   * The constructor.
   */
  public DiagnosticContextFacadeImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCorrelationId() {

    return MDC.get(LoggingConstants.CORRELATION_ID);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCorrelationId(String correlationId) {

    MDC.put(LoggingConstants.CORRELATION_ID, correlationId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeCorrelationId() {

    MDC.remove(LoggingConstants.CORRELATION_ID);
  }

}
