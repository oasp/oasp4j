package io.oasp.gastronomy.restaurant.general.common.api.exception;

import io.oasp.gastronomy.restaurant.general.common.api.NlsBundleApplicationRoot;

/**
 * This exception is thrown if an {@link io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity entity} has
 * a specific state that is illegal for the current operation and caused it to fail.
 *
 */
public class IllegalPropertyChangeException extends ApplicationBusinessException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param object the object that caused this error.
   * @param property is the property that can not be changed (typically a {@link String}).
   */
  public IllegalPropertyChangeException(Object object, Object property) {

    this((Throwable) null, object, property);
  }

  /**
   * The constructor.
   *
   * @param cause the {@link #getCause() cause} of this error.
   * @param object the object that caused this error.
   * @param property is the property that can not be changed (typically a {@link String}).
   */
  public IllegalPropertyChangeException(Throwable cause, Object object, Object property) {

    super(cause, createBundle(NlsBundleApplicationRoot.class).errorIllegalPropertyChange(object, property));
  }

}
