package io.oasp.module.rest.service.impl;

import java.util.Map;

import javax.validation.ValidationException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;
import net.sf.mmm.util.exception.api.TechnicalErrorUserException;
import net.sf.mmm.util.security.api.SecurityErrorUserException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test-case for {@link RestServiceExceptionFacade}.
 *
 * @author hohwille
 */
public class RestServiceExceptionFacadeTest extends Assert {

  /** Value of {@link TechnicalErrorUserException#getCode()}. */
  private static final String CODE_TECHNICAL_ERROR = "TechnicalError";

  /** Placeholder for any UUID. */
  private static final String UUID_ANY = "<any-uuid>";

  /**
   * @return the {@link RestServiceExceptionFacade} instance to test.
   */
  protected RestServiceExceptionFacade getExceptionFacade() {

    RestServiceExceptionFacade facade = new RestServiceExceptionFacade();
    facade.setMapper(new ObjectMapper());
    return facade;
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with forbidden security exception including
   * subclasses.
   */
  @Test
  public void testSecurityExceptions() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();

    String secretMessage = "Secret information not to be revealed on client - only to be logged on server!";

    int statusCode = 403;
    String message = "forbidden";
    String code = null;

    checkFacade(exceptionFacade, new AccessDeniedException(secretMessage), statusCode, message, UUID_ANY, code);
    checkFacade(exceptionFacade, new AuthenticationCredentialsNotFoundException(secretMessage), statusCode, message,
        UUID_ANY, code);
    checkFacade(exceptionFacade, new BadCredentialsException(secretMessage), statusCode, message, UUID_ANY, code);
    checkFacade(exceptionFacade, new AccountExpiredException(secretMessage), statusCode, message, UUID_ANY, code);
    checkFacade(exceptionFacade, new InternalAuthenticationServiceException(secretMessage), statusCode, message,
        UUID_ANY, code);
    SecurityErrorUserException error = new SecurityErrorUserException();
    checkFacade(exceptionFacade, error, statusCode, message, error.getUuid().toString(), code);
  }

  /**
   * Checks that the specified {@link RestServiceExceptionFacade} provides the expected results for the given
   * {@link Throwable}.
   *
   * @param exceptionFacade is the {@link RestServiceExceptionFacade} to test.
   * @param error is the {@link Throwable} to convert.
   * @param statusCode is the expected {@link Response#getStatus() status} code.
   * @param message is the expected {@link Throwable#getMessage() error message} from the JSON result.
   * @param uuid is the expected {@link NlsRuntimeException#getUuid() UUID} from the JSON result. May be
   *        <code>null</code>.
   * @param code is the expected {@link NlsRuntimeException#getCode() error code} from the JSON result. May be
   *        <code>null</code>.
   * @return the JSON result for potential further asserts.
   */
  protected String checkFacade(RestServiceExceptionFacade exceptionFacade, Throwable error, int statusCode,
      String message, String uuid, String code) {

    Response response = exceptionFacade.toResponse(error);
    assertNotNull(response);
    assertEquals(statusCode, response.getStatus());

    Object entity = response.getEntity();
    assertTrue(entity instanceof String);
    String result = (String) entity;

    if (statusCode == 403) {
      assertFalse(result.contains(error.getMessage()));
    }
    try {
      Map<String, String> valueMap = exceptionFacade.getMapper().readValue(result, Map.class);
      String msg = message;
      if (msg == null) {
        msg = error.getLocalizedMessage();
      }
      assertEquals(msg, valueMap.get(RestServiceExceptionFacade.KEY_MESSAGE));
      assertEquals(code, valueMap.get(RestServiceExceptionFacade.KEY_CODE));
      String actualUuid = valueMap.get(RestServiceExceptionFacade.KEY_UUID);
      if (UUID_ANY.equals(uuid)) {
        if (actualUuid == null) {
          fail("UUID expected but not found in response: " + result);
        }
      } else {
        assertEquals(uuid, actualUuid);
      }
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
    return result;
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with bad request technical exception including
   * subclasses.
   */
  @Test
  public void testJaxrsInternalServerException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    String internalMessage = "The HTTP request is invalid";
    int statusCode = 500;
    InternalServerErrorException error = new InternalServerErrorException(internalMessage);
    String expectedMessage = new TechnicalErrorUserException(error).getLocalizedMessage();
    checkFacade(exceptionFacade, error, statusCode, expectedMessage, UUID_ANY, CODE_TECHNICAL_ERROR);
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
    assertTrue(entity instanceof String);
    String result = (String) entity;
    assertTrue(result.contains(secretMessage));
    response = exceptionFacade.toResponse(new ValidationException(secretMessage));
    assertEquals(400, response.getStatus());
    entity = response.getEntity();
    assertTrue(entity instanceof String);
    result = (String) entity;
    assertTrue(result.contains(secretMessage));
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with bad request technical exception including
   * subclasses.
   */
  @Test
  public void testJaxrsNotFoundException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    String internalMessage = "Either the service URL is wrong or the requested resource does not exist";
    checkFacade(exceptionFacade, new NotFoundException(internalMessage), 404, internalMessage, UUID_ANY, "404");
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with bad request technical exception including
   * subclasses.
   */
  @Test
  public void testTechnicalJavaRuntimeServerException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    String secretMessage = "Internal server error occurred";
    IllegalArgumentException error = new IllegalArgumentException(secretMessage);
    String expectedMessage = new TechnicalErrorUserException(error).getLocalizedMessage();
    checkFacade(exceptionFacade, error, 500, expectedMessage, UUID_ANY, CODE_TECHNICAL_ERROR);
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with bad request technical exception including
   * subclasses.
   */
  @Test
  public void testTechnicalCustomRuntimeServerException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    String message = "Internal server error occurred";
    IllegalCaseException error = new IllegalCaseException(message);
    String expectedMessage = new TechnicalErrorUserException(error).getLocalizedMessage();
    checkFacade(exceptionFacade, error, 500, expectedMessage, error.getUuid().toString(), CODE_TECHNICAL_ERROR);
  }

  /**
   * Tests {@link RestServiceExceptionFacade#toResponse(Throwable)} with bad request technical exception including
   * subclasses.
   */
  @Test
  public void testBusinessException() {

    RestServiceExceptionFacade exceptionFacade = getExceptionFacade();
    ObjectNotFoundUserException error = new ObjectNotFoundUserException(4711L);
    checkFacade(exceptionFacade, error, 400, null, error.getUuid().toString(), "NotFound");
  }
}
