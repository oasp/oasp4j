package io.oasp.module.service.common.api.exception;

import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.exception.api.ExceptionTruncation;
import net.sf.mmm.util.exception.api.NlsThrowable;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.uuid.api.UuidAccess;

import io.oasp.module.basic.common.api.NlsBundleOaspRoot;

/**
 * {@link NlsThrowable NLS}-{@link RuntimeException} thrown if a service invocation failed.
 *
 * @since 3.0.0
 */
public class ServiceInvocationFailedException extends RuntimeException implements NlsThrowable {

  private static final long serialVersionUID = 1L;

  private String code;

  private UUID uuid;

  private NlsMessage message;

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param code the {@link #getCode() code}.
   * @param uuid {@link UUID} the {@link #getUuid() UUID}.
   * @param service the name (e.g. {@link Class#getName() qualified name}) of the service that failed.
   */
  public ServiceInvocationFailedException(String message, String code, UUID uuid, String service) {
    this(null, message, code, uuid, service);
  }

  /**
   * The constructor.
   *
   * @param cause the {@link #getCause() cause} of this exception.
   * @param message the {@link #getMessage() message}.
   * @param code the {@link #getCode() code}.
   * @param uuid {@link UUID} the {@link #getUuid() UUID}.
   * @param service the name (e.g. {@link Class#getName() qualified name}) of the service that failed.
   */
  public ServiceInvocationFailedException(Throwable cause, String message, String code, UUID uuid, String service) {
    super(cause);
    this.message = NlsAccess.getBundleFactory().createBundle(NlsBundleOaspRoot.class) //
        .errorServiceInvocationFailed(service, message);
    this.code = code;
    if (uuid == null) {
      this.uuid = UuidAccess.getFactory().createUuid();
    } else {
      this.uuid = uuid;
    }
  }

  @Override
  public String getMessage() {

    return this.message.getLocalizedMessage();
  }

  @Override
  public NlsMessage toNlsMessage() {

    return this.message;
  }

  @Override
  public UUID getUuid() {

    return this.uuid;
  }

  @Override
  public String getCode() {

    return this.code;
  }

  @Override
  public boolean isTechnical() {

    return false;
  }

  @Override
  public boolean isForUser() {

    return true;
  }

  @Override
  public NlsMessage getNlsMessage() {

    return this.message;
  }

  @Override
  public String getLocalizedMessage(Locale locale) {

    return this.message.getLocalizedMessage(locale);
  }

  @Override
  public void getLocalizedMessage(Locale locale, Appendable appendable) {

    this.message.getLocalizedMessage(locale, appendable);
  }

  @Override
  public void printStackTrace(Locale locale, Appendable buffer) throws IllegalStateException {

    super.printStackTrace();
  }

  @Override
  public Throwable createCopy(ExceptionTruncation truncation) {

    return null;
  }

  @Override
  public String toString(Locale locale) {

    return super.toString();
  }

  @Override
  public Appendable toString(Locale locale, Appendable appendable) {

    return appendable;
  };

}
