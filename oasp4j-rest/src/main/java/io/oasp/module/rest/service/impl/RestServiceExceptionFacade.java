package io.oasp.module.rest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.exception.api.TechnicalErrorUserException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is an implementation of {@link ExceptionMapper} that acts as generic exception facade for REST services.
 *
 * the exception handling class for all upcoming exceptions thrown at REST requests. Each type of possible thrown
 * exception will be fetched within the method "toResponse".
 *
 * @author agreul
 */
@Provider
public class RestServiceExceptionFacade implements ExceptionMapper<Throwable> {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(RestServiceExceptionFacade.class);

  private final List<Class<? extends Throwable>> securityExceptions;

  private ObjectMapper mapper;

  /**
   * The constructor.
   */
  public RestServiceExceptionFacade() {

    super();
    this.securityExceptions = new ArrayList<>();
    registerToplevelSecurityExceptions();
  }

  /**
   * Registers a {@link Class} as a top-level security {@link Throwable exception}. Instances of this class and all its
   * subclasses will be handled as security errors. Therefore an according HTTP error code is used and no further
   * details about the exception is send to the client to prevent <a
   * href="https://www.owasp.org/index.php/Top_10_2013-A6-Sensitive_Data_Exposure">sensitive data exposure</a>.
   *
   * @param securityException is the {@link Class} reflecting the security error.
   */
  protected void registerToplevelSecurityException(Class<? extends Throwable> securityException) {

    this.securityExceptions.add(securityException);
  }

  /**
   * This method registers the {@link #registerToplevelSecurityException(Class) top-level security exceptions}. You may
   * override it to add additional or other classes.
   */
  protected void registerToplevelSecurityExceptions() {

    this.securityExceptions.add(SecurityException.class);
    registerToplevelSecurityExceptions("org.springframework.security.access.AccessDeniedException");
    registerToplevelSecurityExceptions("org.springframework.security.authentication.AuthenticationServiceException");
    registerToplevelSecurityExceptions("org.springframework.security.authentication.AuthenticationCredentialsNotFoundException");
    registerToplevelSecurityExceptions("org.springframework.security.authentication.BadCredentialsException");
    registerToplevelSecurityExceptions("org.springframework.security.authentication.AccountExpiredException");
  }

  /**
   * @param className the className to be registered
   */
  protected void registerToplevelSecurityExceptions(String className) {

    try {
      @SuppressWarnings("unchecked")
      Class<? extends Throwable> securityException = (Class<? extends Throwable>) Class.forName(className);
      registerToplevelSecurityException(securityException);
    } catch (ClassNotFoundException e) {
      LOG.info(
          "Spring security was not found on classpath. Spring security exceptions will not be handled as such by {}",
          getClass().getSimpleName());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Response toResponse(Throwable exception) {

    // business exceptions
    if (exception instanceof ServerErrorException) {
      LOG.error("Service failed on server", exception);
      return addErrorResponseMessage(exception, ((ServerErrorException) exception).getResponse());
    } else if (exception instanceof WebApplicationException) {
      LOG.warn("Service failed due to unexpected request: {}", exception.toString());
      return addErrorResponseMessage(exception, ((WebApplicationException) exception).getResponse());
    } else if (exception instanceof ValidationException) {
      LOG.warn("Service failed due to validation exception: {}", exception.toString());
      String message = createErrorResponseMessage(exception.getMessage(), null, null);
      return Response.status(Status.BAD_REQUEST).entity(message).build();
    } else {
      Class<?> exceptionClass = exception.getClass();
      for (Class<?> securityError : this.securityExceptions) {
        if (securityError.isAssignableFrom(exceptionClass)) {
          return handleSecurityError(exception);
        }
      }
      return handleGenericError(exception);
    }
  }

  /**
   * Exception handling depending on technical Exception or not.
   *
   * @param exception the exception thrown
   * @return the response build from error status
   */
  protected Response handleGenericError(Throwable exception) {

    NlsRuntimeException userError = TechnicalErrorUserException.getOrCreateUserException(exception);
    if (userError.isTechnical()) {
      LOG.error("Service failed on server", exception);
    } else {
      LOG.warn("Service failed due to business error: {}", exception.getMessage());
    }
    return createResponse(userError);
  }

  /**
   * Exception handling for security exceptions.
   *
   * @param exception the exception thrown
   * @return the response build from error status
   */
  protected Response handleSecurityError(Throwable exception) {

    // *** security error ***
    LOG.error("Service failed due to security error", exception);
    // NOTE: for security reasons we do not send any details about the error
    // to the client!
    String message = createErrorResponseMessage("forbidden", null, null);
    return Response.status(Status.FORBIDDEN).entity(message).build();
  }

  /**
   * Responsible for creating the technical error response message.
   *
   * @param error the thrown technical error user exception
   * @return complete error response message as JSON-String
   */
  protected Response createResponse(NlsRuntimeException error) {

    if (error.isTechnical()) {
      String message = createErrorResponseMessage(error.getMessage(), error.getCode(), error.getUuid().toString());
      return Response.status(Status.INTERNAL_SERVER_ERROR).entity(message).build();
    } else {
      String message = createErrorResponseMessage(error.getMessage(), null, error.getUuid().toString());
      LOG.warn("Service failed due to business error: {}", message);
      return Response.status(Status.BAD_REQUEST).entity(message).build();
    }
  }

  /**
   * Create a response message as a JSON-String from the given parts.
   *
   * @param message the message of the response message
   * @param code the code of the response message
   * @param uuid the uuid of the response message
   * @return the response message as a JSON-String
   */
  protected String createErrorResponseMessage(String message, String code, String uuid) {

    Map<String, String> jsonMap = new HashMap<>();
    if (message != null) {
      jsonMap.put("message", message);
    }
    if (code != null) {
      jsonMap.put("code", code);
    }
    if (uuid != null) {
      jsonMap.put("uuid", uuid);
    }

    setMapper(new ObjectMapper());
    String responseMessage = "";
    try {
      responseMessage = this.mapper.writeValueAsString(jsonMap);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return responseMessage;
  }

  /**
   * Add a response message to an existing response.
   *
   * @param exception the exception a response is needed for
   * @param response the generated response from the exception
   * @return the response with the response message added
   */
  protected Response addErrorResponseMessage(Throwable exception, Response response) {

    String message = createErrorResponseMessage(exception.getMessage(), null, null);
    response.getEntity();
    Response newResponse = Response.status(response.getStatus()).entity(message).build();
    return newResponse;
  }

  /**
   * @param mapper the mapper to set
   */
  @Inject
  public void setMapper(ObjectMapper mapper) {

    this.mapper = mapper;
  }
}
