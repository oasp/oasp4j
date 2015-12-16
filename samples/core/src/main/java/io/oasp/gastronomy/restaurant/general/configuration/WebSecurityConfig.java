package io.oasp.gastronomy.restaurant.general.configuration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import io.oasp.module.security.common.api.accesscontrol.AccessControlProvider;
import io.oasp.module.security.common.base.accesscontrol.AccessControlSchemaProvider;
import io.oasp.module.security.common.impl.accesscontrol.AccessControlProviderImpl;
import io.oasp.module.security.common.impl.accesscontrol.AccessControlSchemaProviderImpl;
import io.oasp.module.security.common.impl.rest.LogoutSuccessHandlerReturningOkHttpStatusCode;

/**
 * Security configuration based on {@link WebSecurityConfigurerAdapter}.
 *
 * @author hohwille
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Inject
  private AuthenticationManagerBuilder authenticationManagerBuilder;

  @Inject
  private AuthenticationProvider authenticationProvider;

  @Bean
  public AccessControlProvider accessControlProvider() {

    return new AccessControlProviderImpl();
  }

  @Bean
  public AccessControlSchemaProvider accessControlSchemaProvider() {

    return new AccessControlSchemaProviderImpl();
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests().antMatchers("/", "/security/**").permitAll().anyRequest().authenticated().and() //
        .authenticationProvider(this.authenticationProvider).formLogin()
        .successHandler(accessAuthenticationSuccessHandler()) //
        .failureUrl("/security/login.html?error").and() //
        .logout().logoutSuccessHandler(new LogoutSuccessHandlerReturningOkHttpStatusCode());
  }

  @Bean
  public static DefaultRolesPrefixPostProcessor defaultRolesPrefixPostProcessor() {

    // By default Spring-Security is setting the prefix "ROLE_" for all permissions/authorities.
    // We disable this undesired behavior here...
    return new DefaultRolesPrefixPostProcessor("");
  }

  protected AuthenticationSuccessHandler accessAuthenticationSuccessHandler() {

    SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
    authenticationSuccessHandler.setTargetUrlParameter("targetUrl");
    return authenticationSuccessHandler;
  }

  @PostConstruct
  public void init() throws Exception {

    this.authenticationManagerBuilder.inMemoryAuthentication() //
        .withUser("waiter").password("waiter").roles("Waiter").and() //
        .withUser("cook").password("cook").roles("Cook").and() //
        .withUser("barkeeper").password("barkeeper").roles("Barkeeeper") //
        .and().withUser("chief").password("chief").roles("Chief");
  }
}
