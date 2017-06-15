package io.oasp.gastronomy.restaurant.general.common.api.exception;

import io.oasp.gastronomy.restaurant.general.common.api.NlsBundleApplicationRoot;

/**
 * This exception is thrown if an {@link io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity entity} has
 * a specific state that is illegal for the current operation and caused it to fail.
 *
 */
public class IllegalEntityStateException extends ApplicationBusinessException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param entity the entity that caused this error.
   * @param state the state of the entity that is illegal for the failed operation.
   */
  public IllegalEntityStateException(Object entity, Object state) {

    this((Throwable) null, entity, state);
  }

  /**
   * The constructor.
   *
   * @param entity the entity that caused this error.
   * @param currentState the state of the RestaurantEntity entity.
   * @param newState is the new state for the entity that is illegal.
   */
  public IllegalEntityStateException(Object entity, Object currentState, Object newState) {

    this(null, entity, currentState, newState);
  }

  /**
   * The constructor.
   *
   * @param cause the {@link #getCause() cause} of this error.
   * @param entity the entity that caused this error.
   * @param state the state of the entity that is illegal for the failed operation.
   */
  public IllegalEntityStateException(Throwable cause, Object entity, Object state) {

    super(cause, createBundle(NlsBundleApplicationRoot.class).errorIllegalEntityState(entity, state));
  }

  /**
   * The constructor.
   *
   * @param cause the {@link #getCause() cause} of this error.
   * @param entity the entity that caused this error.
   * @param currentState the state of the entity.
   * @param newState is the new state for the entity that is illegal.
   */
  public IllegalEntityStateException(Throwable cause, Object entity, Object currentState, Object newState) {

    super(cause, createBundle(NlsBundleApplicationRoot.class).errorIllegalEntityStateChange(entity, currentState,
        newState));
  }

}
