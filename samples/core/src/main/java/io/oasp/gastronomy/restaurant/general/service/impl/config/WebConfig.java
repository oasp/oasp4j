package io.oasp.gastronomy.restaurant.general.service.impl.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import io.oasp.module.logging.common.api.DiagnosticContextFacade;
import io.oasp.module.logging.common.impl.DiagnosticContextFacadeImpl;
import io.oasp.module.logging.common.impl.DiagnosticContextFilter;
import io.oasp.module.logging.common.impl.PerformanceLogFilter;

/**
 * Registers a number of filters for web requests.
 *
 */
@Configuration
public class WebConfig {

  private @Autowired AutowireCapableBeanFactory beanFactory;

  /**
   * Register PerformanceLogFilter to log running time of requests.
   *
   * @return filter
   */
  @Bean
  public FilterRegistrationBean performanceLogFilter() {

    FilterRegistrationBean registration = new FilterRegistrationBean();
    Filter performanceLogFilter = new PerformanceLogFilter();
    this.beanFactory.autowireBean(performanceLogFilter);
    registration.setFilter(performanceLogFilter);
    registration.addUrlPatterns("/*");
    return registration;
  }

  /**
   * Bean definition for DiagnosticContextFacade.
   *
   * @return DiagnosticContextFacade
   */
  @Bean(name = "DiagnosticContextFacade")
  public DiagnosticContextFacade diagnosticContextFacade() {

    return new DiagnosticContextFacadeImpl();
  }

  /**
   * Register DiagnosticContextFilter to log service calls with correlation id.
   *
   * @return filter
   */
  @Bean
  public FilterRegistrationBean diagnosticContextFilter() {

    FilterRegistrationBean registration = new FilterRegistrationBean();
    Filter diagnosticContextFilter = new DiagnosticContextFilter();
    this.beanFactory.autowireBean(diagnosticContextFilter);
    registration.setFilter(diagnosticContextFilter);
    registration.addUrlPatterns("/services/*");
    return registration;
  }

  /**
   * Register SetCharacterEncodingFilter to convert specical characters correctly.
   *
   * @return filter
   */
  @Bean
  public FilterRegistrationBean setCharacterEncodingFilter() {

    FilterRegistrationBean registration = new FilterRegistrationBean();
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("UTF-8");
    characterEncodingFilter.setForceEncoding(false);
    this.beanFactory.autowireBean(characterEncodingFilter);
    registration.setFilter(characterEncodingFilter);
    registration.addUrlPatterns("/*");
    return registration;
  }
}