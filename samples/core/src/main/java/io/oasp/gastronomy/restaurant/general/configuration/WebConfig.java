package io.oasp.gastronomy.restaurant.general.configuration;

import io.oasp.module.logging.common.api.DiagnosticContextFacade;
import io.oasp.module.logging.common.impl.DiagnosticContextFacadeImpl;
import io.oasp.module.logging.common.impl.DiagnosticContextFilter;
import io.oasp.module.logging.common.impl.PerformanceLogFilter;

import javax.servlet.Filter;

import org.apache.catalina.filters.SetCharacterEncodingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers a number of filters for web requests.
 *
 * @author rose
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
    SetCharacterEncodingFilter setCharacterEncodingFilter = new SetCharacterEncodingFilter();
    setCharacterEncodingFilter.setEncoding("UTF-8");
    setCharacterEncodingFilter.setIgnore(false);
    this.beanFactory.autowireBean(setCharacterEncodingFilter);
    registration.setFilter(setCharacterEncodingFilter);
    registration.addUrlPatterns("/*");
    return registration;
  }

  // <!-- Web Sockets
  // <servlet>
  // <servlet-name>web-socket-dispatcher</servlet-name>
  // <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  // <init-param>
  // <param-name>contextConfigLocation</param-name>
  // <param-value>classpath:config/app/websocket/websocket-context.xml</param-value>
  // </init-param>
  // <load-on-startup>1</load-on-startup>
  // <async-supported>true</async-supported>
  // </servlet>
  // <servlet-mapping>
  // <servlet-name>web-socket-dispatcher</servlet-name>
  // <url-pattern>/websocket/*</url-pattern>
  // </servlet-mapping>

}