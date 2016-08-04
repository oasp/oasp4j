package io.oasp.gastronomy.restaurant.general.configuration;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.oasp.gastronomy.restaurant.general.common.impl.security.CsrfRequestMatcher;
import io.oasp.module.security.common.impl.rest.AuthenticationSuccessHandlerSendingOkHttpStatusCode;
import io.oasp.module.security.common.impl.rest.JsonUsernamePasswordAuthenticationFilter;
import io.oasp.module.security.common.impl.rest.LogoutSuccessHandlerReturningOkHttpStatusCode;

/**
 * This type serves as a base class for extensions of the {@code WebSecurityConfigurerAdapter} and provides a default
 * configuration. <br/>
 * Security configuration is based on {@link WebSecurityConfigurerAdapter}. This configuration is by purpose designed
 * most simple for two channels of authentication: simple login form and rest-url.
 *
 * @author jmolinar
 */
public abstract class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${security.cors.enabled}")
  boolean corsEnabled = false;

  @Inject
  protected AuthenticationManagerBuilder authenticationManagerBuilder;

  @Inject
  protected UserDetailsService userDetailsService;

  // // By default Spring-Security is setting the prefix "ROLE_" for all permissions/authorities.
  // // We disable this undesired behavior here...
  // return new DefaultRolesPrefixPostProcessor("");
  // }

  private CorsFilter getCorsFilter() {

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("OPTIONS");
    config.addAllowedMethod("HEAD");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("DELETE");
    config.addAllowedMethod("PATCH");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  /**
   * Configure spring security to enable a simple webform-login + a simple rest login.
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {

    String[] unsecuredResources =
        new String[] { "/login", "/security/**", "/services/rest/login", "/services/rest/logout" };

    http
        //
        // define all urls that are not to be secured
        .userDetailsService(userDetailsService()).userDetailsService(inMemoryUserDetailsManager()).authorizeRequests()
        .antMatchers(unsecuredResources).permitAll().anyRequest().authenticated().and()

        // activate crsf check for a selection of urls (but not for login & logout)
        .csrf().requireCsrfProtectionMatcher(new CsrfRequestMatcher()).and()

        // configure parameters for simple form login (and logout)
        .formLogin().successHandler(new SimpleUrlAuthenticationSuccessHandler()).defaultSuccessUrl("/")
        .failureUrl("/login.html?error").loginProcessingUrl("/j_spring_security_login").usernameParameter("username")
        .passwordParameter("password").and()
        // logout via POST is possible
        .logout().logoutSuccessUrl("/login.html").and()

        // register login and logout filter that handles rest logins
        // .addFilterBefore(basicAuthenticationFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(getSimpleRestAuthenticationFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(getSimpleRestLogoutFilter(), LogoutFilter.class);

    if (this.corsEnabled) {
      http.addFilterBefore(getCorsFilter(), CsrfFilter.class);
    }
  }

  /**
   * Create a simple filter that allows logout on a REST Url /services/rest/logout and returns a simple HTTP status 200
   * ok.
   *
   * @return the filter.
   */
  protected Filter getSimpleRestLogoutFilter() {

    LogoutFilter logoutFilter =
        new LogoutFilter(new LogoutSuccessHandlerReturningOkHttpStatusCode(), new SecurityContextLogoutHandler());

    // configure logout for rest logouts
    logoutFilter.setLogoutRequestMatcher(new AntPathRequestMatcher("/services/rest/logout"));

    return logoutFilter;
  }

  /**
   * Create a simple authentication filter for REST logins that reads user-credentials from a json-parameter and returns
   * status 200 instead of redirect after login.
   *
   * @return the AuthenticationFilter
   * @throws Exception
   */
  protected JsonUsernamePasswordAuthenticationFilter getSimpleRestAuthenticationFilter() throws Exception {

    JsonUsernamePasswordAuthenticationFilter jsonFilter =
        new JsonUsernamePasswordAuthenticationFilter(new AntPathRequestMatcher("/services/rest/login"));
    jsonFilter.setPasswordParameter("j_password");
    jsonFilter.setUsernameParameter("j_username");
    jsonFilter.setAuthenticationManager(authenticationManager());
    // set failurehandler that uses no redirect in case of login failure; just HTTP-status: 401
    jsonFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
    // set successhandler that uses no redirect in case of login success; just HTTP-status: 200
    jsonFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerSendingOkHttpStatusCode());
    return jsonFilter;
  }

  /**
   * Init the authenticationManager and simply set users and roles here (to keep things as simplistic as possible).
   *
   * @throws Exception
   */
  @PostConstruct
  public void init() throws Exception {

    this.authenticationManagerBuilder.inMemoryAuthentication(); //

  }

  @Override
  protected UserDetailsService userDetailsService() {

    return this.userDetailsService;
  }

  /**
   * @return bean with pre populated users with passwords and roles.
   */
  @Bean
  public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

    final Properties users = new Properties();
    users.put("waiter", "waiter,Waiter");
    users.put("cook", "cook,Cook");
    users.put("barkeeper", "barkeeper,Barkeeper");
    users.put("chief", "chief,Chief");// add whatever other user you need
    return new InMemoryUserDetailsManager(users);
  }

}
