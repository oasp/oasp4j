package org.oasp.module.logging.common.impl;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.oasp.module.logging.common.api.LoggingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Request logging filter that adds the request log message to the SLF4j mapped
 * diagnostic context (MDC) before the request is processed, removing it again
 * after the request is processed.
 * 
 * @author <a href="malte.brunnlieb@capgemini.com">Malte Brunnlieb</a>
 * @version $Revision$
 */
public class Slf4jMdcFilter implements Filter {

  private static final Logger LOG = LoggerFactory.getLogger(Slf4jMdcFilter.class);

  private String correlationIdHttpHeaderName;

  /**
   * Set the correlation header name
   * 
   * @param correlationIdHttpHeaderName header name for the correlation id
   */
  public void setCorrelationIdHttpHeaderName(String correlationIdHttpHeaderName) {

    this.correlationIdHttpHeaderName = correlationIdHttpHeaderName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {

    String correlationId = MDC.get(LoggingConstants.CORRELATION_ID);
    if (request instanceof HttpServletRequest && this.correlationIdHttpHeaderName != null) {
      correlationId = ((HttpServletRequest) request).getHeader(this.correlationIdHttpHeaderName);
    }

    if (correlationId == null || correlationId.trim().isEmpty()) {
      correlationId = UUID.randomUUID().toString();
      MDC.put(LoggingConstants.CORRELATION_ID, correlationId);
      if (LOG.isDebugEnabled()) {
        if (this.correlationIdHttpHeaderName != null) {
          LOG.debug("HTTP-Header " + this.correlationIdHttpHeaderName + " enthält keine "
              + "Correlation-ID. Neue Correlation-ID " + correlationId + " erzeugt.");
        } else {
          LOG.debug("Name des HTTP-Header mit Correlation-ID ist nicht definiert. " + "" + "Neue Correlation-ID "
              + correlationId + " erzeugt.");
        }
      }
    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Übernehme Correlation-ID " + correlationId + " aus HTTP-Header " + this.correlationIdHttpHeaderName);
      }
    }

    try {
      chain.doFilter(request, response);
    } finally {
      MDC.remove(LoggingConstants.CORRELATION_ID);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(FilterConfig arg0) throws ServletException {

  }

}
