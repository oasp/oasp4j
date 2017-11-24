package io.oasp.gastronomy.restaurant.general.common.api.exception;

import io.oasp.gastronomy.restaurant.general.common.api.NlsBundleApplicationRoot;

/**
 * Thrown when an operation is requested that requires a user to be logged in, but no such user exists.
 *
 */
public class IllegalHeaderValueException extends ApplicationBusinessException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param name header name
   * @param value header value
   */
  public IllegalHeaderValueException(final String name, final String value) {

    super(null, createBundle(NlsBundleApplicationRoot.class).errorIllegalHeaderValue(name, value));
  }

}
