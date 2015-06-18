package io.oasp.module.web.common.base;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for wrapping a {@link Filter} and allows to be {@link #setDisabled(Boolean) disabled} e.g.
 * for development tests (e.g. via some {@link System#getProperty(String) system property}. In case the filter gets
 * {@link #setDisabled(Boolean) disabled} a WARNING log message is produced and also written to {@link System#err}. <br/>
 *
 * Here is an example spring XML config from our sample application that allows to disable the <code>CsrfFilter</code>
 * via a {@link System#getProperties() system property} (<code>-DCsrfDisabled=true</code>):
 *
 * <pre>
 * &lt;bean id="CsrfFilterWrapper" class="io.oasp.gastronomy.restaurant.general.service.impl.rest.ToggleFilterWrapper">
 *   &lt;property name="delegateFilter" ref="CsrfFilter"/>
 *   &lt;property name="disabledString" value="#{systemProperties['CsrfDisabled']}"/>
 * &lt;/bean>
 * </pre>
 *
 * @author hohwille
 */
public class ToggleFilterWrapper implements Filter {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(ToggleFilterWrapper.class);

  /**
   * The delegated Filter.
   */
  private Filter delegateFilter;

  /**
   * Is set if this filter is disabled.
   */
  private Boolean disabled;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  /**
   *
   */
  @PostConstruct
  public void initialize() {

    if (this.disabled) {
      String message =
          "****** FILTER " + this.delegateFilter
              + " HAS BEEN DISABLED! THIS FEATURE SHOULD ONLY BE USED IN DEVELOPMENT MODE ******";
      LOG.warn(message);
      // CHECKSTYLE:OFF (for development only)
      System.err.println(message);
      // CHECKSTYLE:ON
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {

    if (!this.disabled) {
      this.delegateFilter.doFilter(request, response, chain);
    } else {
      chain.doFilter(request, response);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {

  }

  /**
   * @param delegateFilter the filter to delegate to
   */
  public void setDelegateFilter(Filter delegateFilter) {

    this.delegateFilter = delegateFilter;
  }

  /**
   * @param disabled indicates if this filter is disabled
   */
  public void setDisabled(Boolean disabled) {

    this.disabled = disabled;
  }

  /**
   * @param disabledString the String to be parsed to a boolean
   */
  public void setDisabledString(String disabledString) {

    setDisabled(Boolean.parseBoolean(disabledString));
  }

  /**
   * @return disabled
   */
  public Boolean isDisabled() {

    return this.disabled;
  }
}
