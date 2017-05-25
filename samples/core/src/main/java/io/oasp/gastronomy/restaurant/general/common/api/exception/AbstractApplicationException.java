package io.oasp.gastronomy.restaurant.general.common.api.exception;

import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * Abstract main exception.
 *
 */
public abstract class AbstractApplicationException extends NlsRuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * @param message the error {@link #getNlsMessage() message}.
   */
  public AbstractApplicationException(NlsMessage message) {

    super(message);
  }

  /**
   * @param cause the error {@link #getCause() cause}.
   * @param message the error {@link #getNlsMessage() message}.
   */
  public AbstractApplicationException(Throwable cause, NlsMessage message) {

    super(cause, message);
  }

}
