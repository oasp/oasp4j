package io.oasp.module.security.common.base.accesscontrol;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * TODO sroeger This type ...
 *
 * @author sroeger
 * @since 2.2.0
 */
public class BaseAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {

    // TODO Auto-generated method stub
    return false;
  }

}
