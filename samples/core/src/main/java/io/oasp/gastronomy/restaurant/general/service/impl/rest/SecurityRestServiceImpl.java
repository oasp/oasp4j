package io.oasp.gastronomy.restaurant.general.service.impl.rest;

import io.oasp.gastronomy.restaurant.general.common.api.exception.NoActiveUserException;
import io.oasp.gastronomy.restaurant.general.common.api.security.UserData;
import io.oasp.gastronomy.restaurant.general.common.api.to.UserDetailsClientTo;

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
 * The security REST service provides access to the csrf token, the authenticated user's meta-data. Furthermore, it
 * provides functionality to check permissions and roles of the authenticated user.
 *
 */
@Path("/security/v1")
@Named("SecurityRestService")
@Transactional
public class SecurityRestServiceImpl {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(SecurityRestServiceImpl.class);

  /**
   * Use {@link CsrfTokenRepository} for CSRF protection.
   */
  private CsrfTokenRepository csrfTokenRepository;

  /**
   * Retrieves the CSRF token from the server session.
   *
   * @param request {@link HttpServletRequest} to retrieve the current session from
   * @param response {@link HttpServletResponse} to send additional information
   * @return the Spring Security {@link CsrfToken}
   */
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @Path("/csrftoken/")
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

  /**
   * Gets the profile of the user being currently logged in.
   *
   * @param request provided by the RS-Context
   * @return the {@link UserData} taken from the Spring Security context
   */
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @Path("/currentuser/")
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
