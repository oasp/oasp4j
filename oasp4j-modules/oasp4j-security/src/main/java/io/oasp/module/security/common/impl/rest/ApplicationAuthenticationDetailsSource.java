package io.oasp.module.security.common.impl.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;

/**
 * Custom Authentication Details Source to extract locale information from the request
 *
 * @author dsanche2
 */
public class ApplicationAuthenticationDetailsSource implements
    AuthenticationDetailsSource<HttpServletRequest, ApplicationWebAuthenticationDetails> {

  @Override
  public ApplicationWebAuthenticationDetails buildDetails(HttpServletRequest context) {

    return new ApplicationWebAuthenticationDetails(context);
  }
}