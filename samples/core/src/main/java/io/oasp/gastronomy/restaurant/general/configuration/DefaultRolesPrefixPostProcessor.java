package io.oasp.gastronomy.restaurant.general.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.security.access.annotation.Jsr250MethodSecurityMetadataSource;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

/**
 * By default Spring-Security is setting the prefix "ROLE_" for all permissions/authorities. This class offers the
 * possibility to change this behavior to a wanted prefix using the constructor with a string parameter.
 */
public class DefaultRolesPrefixPostProcessor implements BeanPostProcessor, PriorityOrdered {

  private final String rolePrefix;

  /**
   * The constructor.
   *
   * @param rolePrefix the role prefix to set (e.g. an empty string).
   */
  public DefaultRolesPrefixPostProcessor(String rolePrefix) {
    super();
    this.rolePrefix = rolePrefix;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

    // remove this if you are not using JSR-250
    if (bean instanceof Jsr250MethodSecurityMetadataSource) {
      ((Jsr250MethodSecurityMetadataSource) bean).setDefaultRolePrefix(this.rolePrefix);
    }

    if (bean instanceof DefaultMethodSecurityExpressionHandler) {
      ((DefaultMethodSecurityExpressionHandler) bean).setDefaultRolePrefix(this.rolePrefix);
    }
    if (bean instanceof DefaultWebSecurityExpressionHandler) {
      ((DefaultWebSecurityExpressionHandler) bean).setDefaultRolePrefix(this.rolePrefix);
    }
    if (bean instanceof SecurityContextHolderAwareRequestFilter) {
      ((SecurityContextHolderAwareRequestFilter) bean).setRolePrefix(this.rolePrefix);
    }
    return bean;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

    return bean;
  }

  @Override
  public int getOrder() {

    return PriorityOrdered.HIGHEST_PRECEDENCE;
  }
}
