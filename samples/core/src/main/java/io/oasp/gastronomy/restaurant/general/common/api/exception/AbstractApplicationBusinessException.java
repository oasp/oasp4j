package io.oasp.gastronomy.restaurant.general.common.api.exception;

import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * Abstract business <i>checked</i> main exception.
 *
 */
public abstract class AbstractApplicationBusinessException extends AbstractApplicationException {

  private static final long serialVersionUID = 1L;

  /**
   * @param message the error {@link #getNlsMessage() message}.
   */
  public AbstractApplicationBusinessException(NlsMessage message) {

    super(message);
  }

  /**
   * @param cause the error {@link #getCause() cause}.
   * @param message the error {@link #getNlsMessage() message}.
   */
  public AbstractApplicationBusinessException(Throwable cause, NlsMessage message) {

    super(cause, message);
  }

  @Override
  public boolean isTechnical() {

    return false;
  }

}
