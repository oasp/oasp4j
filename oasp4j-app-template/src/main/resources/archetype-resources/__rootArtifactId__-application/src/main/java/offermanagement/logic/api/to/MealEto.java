#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.api.to;

import ${package}.offermanagement.common.api.Meal;

/**
 * The {@link ${package}.general.common.api.to.AbstractEto ETO} for a {@link Meal}.
 *
 * @author jozitz
 */
public class MealEto extends ProductEto implements Meal {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public MealEto() {

    super();
  }

}
