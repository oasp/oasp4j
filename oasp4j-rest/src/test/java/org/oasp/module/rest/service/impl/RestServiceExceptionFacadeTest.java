package org.oasp.module.rest.service.impl;

import javax.validation.ValidationException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * Test-case for {@link RestServiceExceptionFacade}.
 *
 * @author hohwille
 */
public class RestServiceExceptionFacadeTest extends Assert {

  /**
   * @return the {@link RestServiceExceptionFacade} instance to test.
   */
  protected RestServiceExceptionFacade getExceptionFacade() {

    return new RestServiceExceptionFacade();
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with forbidden security exception including
   * subclasses.
   */
  @Test
  public void testSecurityExceptions() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();

    String secretMessage = "Secret information not to be revealed on client - only to be logged on server!";

    Response response = exceptionFacade.toResponse(new AccessDeniedException(secretMessage));
    assertEquals(403, response.getStatus());
    Object entity = response.getEntity();
    assertNull(entity); // no content
    response = exceptionFacade.toResponse(new AuthenticationCredentialsNotFoundException(secretMessage));
    assertEquals(403, response.getStatus());
    response = exceptionFacade.toResponse(new BadCredentialsException(secretMessage));
    assertEquals(403, response.getStatus());
    response = exceptionFacade.toResponse(new AccountExpiredException(secretMessage));
    assertEquals(403, response.getStatus());
    response = exceptionFacade.toResponse(new InternalAuthenticationServiceException(secretMessage));
    assertEquals(403, response.getStatus());
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with unauthorized security exception including
   * subclasses.
   */
  @Test
  public void testSecurityUnauthorizedException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    String secretMessage = "You are not authorized to view this information";
    Response response = exceptionFacade.toResponse(new AuthenticationServiceException(secretMessage));
    assertEquals(401, response.getStatus());
    Object entity = response.getEntity();
    assertNull(entity); // no content
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with bad request technical exception including
   * subclasses.
   */
  @Test
  public void testJaxrsInternalServerException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    String secretMessage = "The HTTP request is invalid";
    Response response = exceptionFacade.toResponse(new InternalServerErrorException(secretMessage));
    assertEquals(500, response.getStatus());
    Object entity = response.getEntity();
    assertNull(entity); // no content
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with bad request technical exception including
   * subclasses.
   */
  @Test
  public void testJaxrsBadRequestException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    String secretMessage = "The HTTP request is invalid";
    Response response = exceptionFacade.toResponse(new BadRequestException(secretMessage));
    assertEquals(400, response.getStatus());
    Object entity = response.getEntity();
    assertNull(entity); // no content
    response = exceptionFacade.toResponse(new ValidationException(secretMessage));
    assertEquals(400, response.getStatus());
    entity = response.getEntity();
    assertEquals(entity.toString(), "The HTTP request is invalid");
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with bad request technical exception including
   * subclasses.
   */
  @Test
  public void testJaxrsNotFoundException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    String secretMessage = "Either the service URL is wrong or the requested resource does not exist";
    Response response = exceptionFacade.toResponse(new NotFoundException(secretMessage));
    assertEquals(404, response.getStatus());
    Object entity = response.getEntity();
    assertNull(entity); // no content
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with bad request technical exception including
   * subclasses.
   */
  @Test
  public void testTechnicalJavaRuntimeServerException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    String secretMessage = "Internal server error occurred";
    Response response = exceptionFacade.toResponse(new IllegalArgumentException(secretMessage));
    assertEquals(500, response.getStatus());
    Object entity = response.getEntity();
    assertTrue(entity instanceof String);
    String result = (String) entity;
    assertTrue(result.startsWith("TechnicalError:"));
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with bad request technical exception including
   * subclasses.
   */
  @Test
  public void testTechnicalCustomRuntimeServerException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    String secretMessage = "Internal server error occurred";
    IllegalCaseException exception = new IllegalCaseException(secretMessage);
    Response response = exceptionFacade.toResponse(exception);
    assertEquals(500, response.getStatus());
    Object entity = response.getEntity();
    assertTrue(entity instanceof String);
    String result = (String) entity;
    assertEquals("TechnicalError:" + exception.getUuid(), result);
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with bad request technical exception including
   * subclasses.
   */
  @Test
  public void testBusinessException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    ObjectNotFoundUserException exception = new ObjectNotFoundUserException(4711L);
    Response response = exceptionFacade.toResponse(exception);
    assertEquals(400, response.getStatus());
    Object entity = response.getEntity();
    assertTrue(entity instanceof String);
    String result = (String) entity;
    assertEquals(exception.toString(), result);
  }
}
