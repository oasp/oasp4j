package io.oasp.gastronomy.restaurant.general.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import io.oasp.module.security.common.api.accesscontrol.AccessControlProvider;
import io.oasp.module.security.common.base.accesscontrol.AccessControlSchemaProvider;
import io.oasp.module.security.common.impl.accesscontrol.AccessControlProviderImpl;
import io.oasp.module.security.common.impl.accesscontrol.AccessControlSchemaProviderImpl;

/**
 * TODO jmolinar This type ...
 *
 * @author jmolinar
 * @since dev
 */
@Configuration
public class WebSecurityBeansConfig {

  @Bean
  public AccessControlProvider accessControlProvider() {

    return new AccessControlProviderImpl();
  }

  @Bean
  public AccessControlSchemaProvider accessControlSchemaProvider() {

    return new AccessControlSchemaProviderImpl();
  }

  @Bean
  public CsrfTokenRepository csrfTokenRepository() {

    return new HttpSessionCsrfTokenRepository();
  }

  @Bean
  public static DefaultRolesPrefixPostProcessor defaultRolesPrefixPostProcessor() {

    // By default Spring-Security is setting the prefix "ROLE_" for all permissions/authorities.
    // We disable this undesired behavior here...
    return new DefaultRolesPrefixPostProcessor("");
  }
}
