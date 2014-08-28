package org.oasp.module.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

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
  @SuppressWarnings("unchecked")
  protected void registerToplevelSecurityExceptions() {

    this.securityExceptions.add(SecurityException.class);
    try {
      @SuppressWarnings("rawtypes")
      Class springSecurityException = Class.forName("org.springframework.security.access.AccessDeniedException");
      registerToplevelSecurityException(springSecurityException);
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

    LOG.error("Service request failed: ", exception);
    // business exceptions
    if (exception instanceof ServerErrorException) {
      LOG.error("Service failed on server", exception);
      return ((ServerErrorException) exception).getResponse();
    } else if (exception instanceof WebApplicationException) {
      LOG.warn("Service failed due to unexpected request: {}", exception.toString());
      return ((WebApplicationException) exception).getResponse();
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

  private Response handleGenericError(Throwable exception) {

    NlsRuntimeException userError = TechnicalErrorUserException.getOrCreateUserException(exception);
    if (userError.isTechnical()) {
      return handleTechnicalError(exception, userError);
    } else {
      return handleBusinessError(exception);
    }
  }

  private Response handleBusinessError(Throwable exception) {

    // *** business error ***
    String message = exception.toString();
    LOG.warn("Service failed due to business error: {}", message);
    return Response.status(Status.BAD_REQUEST).entity(message).build();
  }

  private Response handleTechnicalError(Throwable exception, NlsRuntimeException userError) {

    // *** technical error ***
    LOG.error("Service failed on server", exception);
    String message = createTechnicalErrorResponseMessage(userError);
    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(message).build();
  }

  private Response handleSecurityError(Throwable exception) {

    // *** security error ***
    LOG.error("Service failed due to security error", exception);
    // NOTE: for security reasons we do not send any details about the error
    // to the client!
    return Response.status(Status.FORBIDDEN).build();
  }

  /**
   * Responsible for creating the technical error response message.
   *
   * @param technicalError the thrown technical error user exception
   * @return complete error response message
   */
  protected String createTechnicalErrorResponseMessage(NlsRuntimeException technicalError) {

    StringBuilder buffer = new StringBuilder(technicalError.getCode());
    buffer.append(':');
    buffer.append(technicalError.getUuid());
    String message = buffer.toString();
    return message;
  }
}
