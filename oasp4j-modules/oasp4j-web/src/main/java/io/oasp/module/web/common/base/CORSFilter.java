package io.oasp.module.web.common.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter enabling cross origin communication with the server. The defaults have been derived from the tomcat defaults
 * (https://tomcat.apache.org/tomcat-7.0-doc/config/filter.html#CORS_Filter).
 *
 * @author Malte Brunnlieb, Capgemini
 */
public class CORSFilter implements Filter {

  /** Default value for header "Access-Control-Allow-Origin" */
  private static final String DEFAULT_ALLOW_ORIGIN = "*";

  /** Default value for header "Access-Control-Allow-Methods" */
  private static final String DEFAULT_ALLOW_METHODS = "GET, POST, HEAD, OPTIONS";

  /** Default value for header "Access-Control-Max-Age" (30min) */
  private static final String DEFAULT_MAX_AGE = "1800";

  /** Default value for header "Access-Control-Allow-Headers" */
  private static final String DEFAULT_ALLOW_HEADERS =
      "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers";

  /** Config value */
  private String allowOrigin = null;

  /** Config value */
  private String allowMethods = null;

  /** Config value */
  private String maxAge = null;

  /** Config value */
  private String allowHeaders = null;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {

    HttpServletResponse httpResonse = (HttpServletResponse) response;
    httpResonse.setHeader("Access-Control-Allow-Origin", this.allowOrigin != null ? this.allowOrigin
        : DEFAULT_ALLOW_ORIGIN);
    httpResonse.setHeader("Access-Control-Allow-Methods", this.allowMethods != null ? this.allowMethods
        : DEFAULT_ALLOW_METHODS);
    httpResonse.setHeader("Access-Control-Max-Age", this.maxAge != null ? this.maxAge : DEFAULT_MAX_AGE);
    httpResonse.setHeader("Access-Control-Allow-Headers", this.allowHeaders != null ? this.allowHeaders
        : DEFAULT_ALLOW_HEADERS);
    chain.doFilter(request, response);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {

  }

  /**
   * @param allowOrigin the allowOrigin to set
   */
  public void setAllowOrigin(String allowOrigin) {

    this.allowOrigin = allowOrigin;
  }

  /**
   * @param allowMethods the allowMethods to set
   */
  public void setAllowMethods(String allowMethods) {

    this.allowMethods = allowMethods;
  }

  /**
   * @param maxAge the maxAge to set
   */
  public void setMaxAge(String maxAge) {

    this.maxAge = maxAge;
  }

  /**
   * @param allowHeaders the allowHeaders to set
   */
  public void setAllowHeaders(String allowHeaders) {

    this.allowHeaders = allowHeaders;
  }

}
