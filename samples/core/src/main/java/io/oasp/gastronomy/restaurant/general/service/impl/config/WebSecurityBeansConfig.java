package io.oasp.gastronomy.restaurant.general.service.impl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import io.oasp.gastronomy.restaurant.general.logic.impl.config.DefaultRolesPrefixPostProcessor;
import io.oasp.module.security.common.api.accesscontrol.AccessControlProvider;
import io.oasp.module.security.common.base.accesscontrol.AccessControlSchemaProvider;
import io.oasp.module.security.common.impl.accesscontrol.AccessControlProviderImpl;
import io.oasp.module.security.common.impl.accesscontrol.AccessControlSchemaProviderImpl;

/**
 * This configuration class provides factory methods for several Spring security related beans.
 *
 */
@Configuration
public class WebSecurityBeansConfig {

  /**
   * This method provides a new instance of {@code AccessControlProvider}
   *
   * @return the newly created {@code AccessControlProvider}
   */
  @Bean
  public AccessControlProvider accessControlProvider() {

    return new AccessControlProviderImpl();
  }

  /**
   * This method provides a new instance of {@code AccessControlSchemaProvider}
   *
   * @return the newly created {@code AccessControlSchemaProvider}
   */
  @Bean
  public AccessControlSchemaProvider accessControlSchemaProvider() {

    return new AccessControlSchemaProviderImpl();
  }

  /**
   * This method provides a new instance of {@code CsrfTokenRepository}
   *
   * @return the newly created {@code CsrfTokenRepository}
   */
  @Bean
  public CsrfTokenRepository csrfTokenRepository() {

    return new HttpSessionCsrfTokenRepository();
  }

  /**
   * This method provides a new instance of {@code DefaultRolesPrefixPostProcessor}
   *
   * @return the newly create {@code DefaultRolesPrefixPostProcessor}
   */
  @Bean
  public static DefaultRolesPrefixPostProcessor defaultRolesPrefixPostProcessor() {

    // By default Spring-Security is setting the prefix "ROLE_" for all permissions/authorities.
    // We disable this undesired behavior here...
    return new DefaultRolesPrefixPostProcessor("");
  }
}
