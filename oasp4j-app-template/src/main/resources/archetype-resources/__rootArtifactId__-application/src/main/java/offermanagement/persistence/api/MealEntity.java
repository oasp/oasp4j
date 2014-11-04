#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.api;

import ${package}.offermanagement.common.api.Meal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * {@link ${package}.general.persistence.api.ApplicationPersistenceEntity Entity} for a {@link Meal}.
 *
 * @author loverbec
 */
@Entity(name = "Meal")
@DiscriminatorValue("Meal")
public class MealEntity extends ProductEntity implements Meal {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public MealEntity() {

    super();
  }

}
