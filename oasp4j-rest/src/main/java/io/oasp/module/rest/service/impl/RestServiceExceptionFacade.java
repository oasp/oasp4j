package io.oasp.module.rest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.exception.api.TechnicalErrorUserException;
import net.sf.mmm.util.security.api.SecurityErrorUserException;
import net.sf.mmm.util.validation.api.ValidationErrorUserException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

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

  /** JSON key for {@link Throwable#getMessage() error message}. */
  public static final String KEY_MESSAGE = "message";

  /** JSON key for {@link NlsRuntimeException#getUuid() error ID}. */
  public static final String KEY_UUID = "uuid";

  /** JSON key for {@link NlsRuntimeException#getCode() error code}. */
  public static final String KEY_CODE = "code";

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
    this.securityExceptions.add(SecurityErrorUserException.class);
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
    if (exception instanceof WebApplicationException) {
      return createResponse((WebApplicationException) exception);
    } else if (exception instanceof ValidationException) {
      ValidationErrorUserException error = new ValidationErrorUserException(exception);
      return createResponse(exception, error);
    } else if (exception instanceof ValidationErrorUserException) {
      return createResponse(exception, (ValidationErrorUserException) exception);
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
   * Creates the {@link Response} for the given validation exception.
   *
   * @param exception is the original validation exception.
   * @param error is the wrapped exception or the same as <code>exception</code>.
   * @return the requested {@link Response}.
   */
  protected Response createResponse(Throwable exception, ValidationErrorUserException error) {

    LOG.warn("Service failed due to validation failure.", error);
    if (exception == error) {
      return createResponse(Status.BAD_REQUEST, error);
    } else {
      return createResponse(Status.BAD_REQUEST, error, exception.getMessage());
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
    NlsRuntimeException error;
    if (exception instanceof NlsRuntimeException) {
      error = (NlsRuntimeException) exception;
    } else {
      error = new SecurityErrorUserException(exception);
    }
    LOG.error("Service failed due to security error", error);
    // NOTE: for security reasons we do not send any details about the error
    // to the client!
    String message = createJsonErrorResponseMessage("forbidden", null, error.getUuid());
    return Response.status(Status.FORBIDDEN).entity(message).build();
  }

  /**
   * Create the {@link Response} for the given {@link NlsRuntimeException}.
   *
   * @param error the generic {@link NlsRuntimeException}.
   * @return the corresponding {@link Response}.
   */
  protected Response createResponse(NlsRuntimeException error) {

    Status status;
    if (error.isTechnical()) {
      status = Status.INTERNAL_SERVER_ERROR;
    } else {
      status = Status.BAD_REQUEST;
    }
    return createResponse(status, error);
  }

  /**
   * Create a response message as a JSON-String from the given parts.
   *
   * @param status is the HTTP {@link Status}.
   * @param error is the catched or wrapped {@link NlsRuntimeException}.
   * @return the corresponding {@link Response}.
   */
  protected Response createResponse(Status status, NlsRuntimeException error) {

    return createResponse(status, error, error.getLocalizedMessage(LocaleContextHolder.getLocale()));
  }

  /**
   * Create a response message as a JSON-String from the given parts.
   *
   * @param status is the HTTP {@link Status}.
   * @param error is the catched or wrapped {@link NlsRuntimeException}.
   * @param message is the JSON message attribute.
   * @return the corresponding {@link Response}.
   */
  protected Response createResponse(Status status, NlsRuntimeException error, String message) {

    return createResponse(status, error, message, error.getCode());
  }

  /**
   * Create a response message as a JSON-String from the given parts.
   *
   * @param status is the HTTP {@link Status}.
   * @param error is the catched or wrapped {@link NlsRuntimeException}.
   * @param message is the JSON message attribute.
   * @param code is the {@link NlsRuntimeException#getCode() error code}.
   * @return the corresponding {@link Response}.
   */
  protected Response createResponse(Status status, NlsRuntimeException error, String message, String code) {

    return createResponse(status, message, code, error.getUuid());
  }

  /**
   * Create a response message as a JSON-String from the given parts.
   *
   * @param status is the HTTP {@link Status}.
   * @param message is the JSON message attribute.
   * @param code is the {@link NlsRuntimeException#getCode() error code}.
   * @param uuid the {@link UUID} of the response message.
   * @return the corresponding {@link Response}.
   */
  protected Response createResponse(Status status, String message, String code, UUID uuid) {

    String json = createJsonErrorResponseMessage(message, code, uuid);
    return Response.status(status).entity(json).build();
  }

  /**
   * Create a response message as a JSON-String from the given parts.
   *
   * @param message the message of the response message
   * @param code the code of the response message
   * @param uuid the uuid of the response message
   * @return the response message as a JSON-String
   */
  protected String createJsonErrorResponseMessage(String message, String code, UUID uuid) {

    Map<String, String> jsonMap = new HashMap<>();
    if (message != null) {
      jsonMap.put(KEY_MESSAGE, message);
    }
    if (code != null) {
      jsonMap.put(KEY_CODE, code);
    }
    if (uuid != null) {
      jsonMap.put(KEY_UUID, uuid.toString());
    }

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
   * @param exception the {@link WebApplicationException}.
   * @return the response with the response message added
   */
  protected Response createResponse(WebApplicationException exception) {

    Response response = exception.getResponse();
    int statusCode = response.getStatus();
    Status status = Status.fromStatusCode(statusCode);
    NlsRuntimeException error;
    if (exception instanceof ServerErrorException) {
      error = new TechnicalErrorUserException(exception);
      LOG.error("Service failed on server", error);
      return createResponse(status, error);
    } else {
      UUID uuid = UUID.randomUUID();
      if (exception instanceof ClientErrorException) {
        LOG.warn("Service failed due to unexpected request. UUDI: {}, reason: {} ", uuid, exception.getMessage());
      } else {
        LOG.warn("Service caused redirect or other error. UUID: {}, reason: {}", uuid, exception.getMessage());
      }
      return createResponse(status, exception.getMessage(), String.valueOf(statusCode), uuid);
    }

  }

  /**
   * @return the {@link ObjectMapper} for JSON mapping.
   */
  public ObjectMapper getMapper() {

    return this.mapper;
  }

  /**
   * @param mapper the mapper to set
   */
  @Inject
  public void setMapper(ObjectMapper mapper) {

    this.mapper = mapper;
  }
}
