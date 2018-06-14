package io.oasp.gastronomy.restaurant.general.logic.base;

/**
 * Abstract base class for any <em>use case</em> in this application. Actual implementations need to be annotated with
 * {@link javax.inject.Named} and {@link io.oasp.gastronomy.restaurant.general.logic.api.UseCase}.
 *
 */
public abstract class AbstractUc extends AbstractGenericEntityUtils {

  /**
   * The constructor.
   */
  public AbstractUc() {

    super();
  }

}
