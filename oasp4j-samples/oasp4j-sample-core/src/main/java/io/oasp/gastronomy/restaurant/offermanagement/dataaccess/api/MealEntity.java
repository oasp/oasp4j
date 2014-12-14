package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.Meal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity persistent entity} for
 * {@link Meal}.
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
