package io.oasp.gastronomy.restaurant.general.common.impl.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

/**
 * Filter enabling cross origin communication with the server.
 * (https://tomcat.apache.org/tomcat-7.0-doc/config/filter.html#CORS_Filter).
 *
 */
public class CORSFilter implements Filter {

  /** Default value for header "Access-Control-Allow-Origin" */
  private static final String DEFAULT_ALLOW_ORIGIN = "*";

  /** Default value for header "Access-Control-Allow-Methods" */
  private static final String DEFAULT_ALLOW_METHODS = "GET,POST,PUT,DELETE,HEAD,OPTIONS";

  /** Default value for header "Access-Control-Max-Age" (30min) */
  private static final String DEFAULT_MAX_AGE = "1800";

  /** Default value for header "Access-Control-Allow-Headers" */
  private static final String DEFAULT_ALLOW_HEADERS =
      "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, correlationid, Cookie, X-CSRF-TOKEN";

  /** Config value */
  private String allowOrigin = null;

  /** Config value */
  private String allowMethods = null;

  /** Config value */
  private String maxAge = null;

  /** Config value */
  private String allowHeaders = null;

  /** Config value */
  private Boolean enabled = Boolean.FALSE;

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

    if (Boolean.FALSE.equals(this.enabled)) {
      chain.doFilter(request, response);
    } else {

      HttpServletRequest httpRequest = (HttpServletRequest) request;
      HttpServletResponse httpResponse = (HttpServletResponse) response;

      String origin = httpRequest.getHeader("origin");

      httpResponse.setHeader("Access-Control-Allow-Origin", StringUtils.hasText(origin) ? origin
          : (this.allowOrigin != null ? this.allowOrigin : DEFAULT_ALLOW_ORIGIN));
      httpResponse.setHeader("Access-Control-Allow-Methods", this.allowMethods != null ? this.allowMethods
          : DEFAULT_ALLOW_METHODS);
      httpResponse.setHeader("Access-Control-Max-Age", this.maxAge != null ? this.maxAge : DEFAULT_MAX_AGE);
      httpResponse.setHeader("Access-Control-Allow-Headers", this.allowHeaders != null ? this.allowHeaders
          : DEFAULT_ALLOW_HEADERS);
      httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

      if (!httpRequest.getMethod().equals("OPTIONS")) {
        chain.doFilter(request, response);
      }
    }
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

  /**
   * @param enabled the enabled flag to set
   */
  public void setEnabled(Boolean enabled) {

    if (enabled != null) {
      this.enabled = enabled;
    }
  }

}
