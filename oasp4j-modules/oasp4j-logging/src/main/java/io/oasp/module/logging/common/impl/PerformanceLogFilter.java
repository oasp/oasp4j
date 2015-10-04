package io.oasp.module.logging.common.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Request logging filter that measures the execution time of a request.
 *
 * @author trippl
 * @since 1.5.0
 */
public class PerformanceLogFilter implements Filter {

  private static final Logger LOG = LoggerFactory.getLogger(PerformanceLogFilter.class);

  /**
   * Optional filter to only measure execution time of requests that match the filter.
   */
  private String urlFilter;

  /**
   * The constructor.
   */
  public PerformanceLogFilter() {

    super();
  }

  @Override
  public void init(FilterConfig config) throws ServletException {

    this.urlFilter = null;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {

    long startTime, endTime, duration;
    String path = ((HttpServletRequest) request).getServletPath();
    String url = ((HttpServletRequest) request).getRequestURL().toString();

    if (this.urlFilter == null || path.matches(this.urlFilter)) {
      startTime = System.nanoTime();
      chain.doFilter(request, response);
      endTime = System.nanoTime();
      duration = TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

      // Log the request url, time taken and the http status code
      LOG.info(url + "," + duration + "," + ((HttpServletResponse) response).getStatus());
    } else {
      chain.doFilter(request, response);
    }
  }

  @Override
  public void destroy() {

    // nothing to do...
  }

}
