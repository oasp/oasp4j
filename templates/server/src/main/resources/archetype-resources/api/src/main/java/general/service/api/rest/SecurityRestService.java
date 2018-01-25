package ${package}.general.service.api.rest;

import ${package}.general.common.api.exception.NoActiveUserException;
import ${package}.general.common.api.to.UserDetailsClientTo;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import io.oasp.module.rest.common.api.RestService;

/**
 * The security REST service provides access to the csrf token, the authenticated user's meta-data. Furthermore, it
 * provides functionality to check permissions and roles of the authenticated user.
 */
@Path("/security/v1")
public interface SecurityRestService extends RestService {

  /**
   * Retrieves the CSRF token from the server session.
   *
   * @param request {@link HttpServletRequest} to retrieve the current session from
   * @param response {@link HttpServletResponse} to send additional information
   * @return the Spring Security {@link CsrfToken}
   */
  @GET
  @Path("/csrftoken/")
  CsrfToken getCsrfToken(@Context HttpServletRequest request, @Context HttpServletResponse response);

  /**
   * Gets the profile of the user being currently logged in.
   *
   * @param request provided by the RS-Context
   * @return the {@link UserData} taken from the Spring Security context
   */
  @GET
  @Path("/currentuser/")
  UserDetailsClientTo getCurrentUser(@Context HttpServletRequest request);

}
