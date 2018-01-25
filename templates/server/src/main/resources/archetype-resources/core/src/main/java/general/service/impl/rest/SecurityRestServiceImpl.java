#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.service.impl.rest;

import ${package}.general.common.api.exception.NoActiveUserException;
import ${package}.general.common.api.security.UserData;
import ${package}.general.common.api.to.UserDetailsClientTo;
import ${package}.general.service.api.rest.SecurityRestService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

/**
 * Implementation of {@link SecurityRestService}.
 */
@Transactional
public class SecurityRestServiceImpl implements SecurityRestService {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(SecurityRestServiceImpl.class);

  /**
   * Use {@link CsrfTokenRepository} for CSRF protection.
   */
  private CsrfTokenRepository csrfTokenRepository;

  @Override
  @PermitAll
  public CsrfToken getCsrfToken(@Context HttpServletRequest request, @Context HttpServletResponse response) {

    // return (CsrfToken) request.getSession().getAttribute(
    // HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN"));
    CsrfToken token = this.csrfTokenRepository.loadToken(request);
    if (token == null) {
      LOG.warn("No CsrfToken could be found - instanciating a new Token");
      token = this.csrfTokenRepository.generateToken(request);
      this.csrfTokenRepository.saveToken(token, request, response);
    }
    return token;
  }

  @Override
  @PermitAll
  public UserDetailsClientTo getCurrentUser(@Context HttpServletRequest request) {

    if (request.getRemoteUser() == null) {
      throw new NoActiveUserException();
    }
    return UserData.get().toClientTo();
  }

  /**
   * @param csrfTokenRepository the csrfTokenRepository to set
   */
  @Inject
  public void setCsrfTokenRepository(CsrfTokenRepository csrfTokenRepository) {

    this.csrfTokenRepository = csrfTokenRepository;
  }
}
