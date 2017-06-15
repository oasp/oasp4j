package io.oasp.gastronomy.restaurant.general.service.impl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.oasp.gastronomy.restaurant.general.service.impl.config.BaseWebSecurityConfig;
import io.oasp.module.basic.common.api.config.SpringProfileConstants;

/**
 * This type provides web security configuration for testing purposes.
 *
 */
@Configuration
@EnableWebSecurity
@Profile(SpringProfileConstants.JUNIT)
public class TestWebSecurityConfig extends BaseWebSecurityConfig {
  private static Logger LOG = LoggerFactory.getLogger(TestWebSecurityConfig.class);

  /**
   * Configure spring security to enable a simple webform-login + a simple rest login.
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {

    super.configure(http);
    // usage of addFilter is sufficient for BasicAuthenticationFilter.class. The filter will be added according to the
    // order defined in:
    // http://docs.spring.io/autorepo/docs/spring-security/4.1.0.RC1/apidocs/org/springframework/security/config/annotation/web/builders/HttpSecurity.html#addFilter-javax.servlet.Filter-
    http.addFilter(basicAuthenticationFilter());

    // Use the following to disable csrf in tests
    http.csrf().disable();

    LOG.debug("used non static class");
  }

  @Bean
  protected BasicAuthenticationFilter basicAuthenticationFilter() throws Exception {

    AuthenticationEntryPoint authenticationEntryPoint = new BasicAuthenticationEntryPoint();
    BasicAuthenticationFilter basicAuthenticationFilter =
        new BasicAuthenticationFilter(authenticationManagerBean(), authenticationEntryPoint);
    LOG.debug("created basicAuthenticationFilter");
    return basicAuthenticationFilter;
  }

}
