package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.Drink;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.envers.Audited;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity persistent entity} for
 * {@link Drink}.
 *
 */
@Entity
@DiscriminatorValue("Drink")
@Audited
public class DrinkEntity extends ProductEntity implements Drink {

  private static final long serialVersionUID = 1L;

  private boolean alcoholic;

  /**
   * The constructor.
   */
  public DrinkEntity() {

    super();
  }

  /**
   * Returns the field 'alcoholic'.
   *
   * @return Value of alcoholic
   */
  @Override
  public boolean isAlcoholic() {

    return this.alcoholic;
  }

  /**
   * Sets the field 'alcoholic'.
   *
   * @param alcoholic New value for alcoholic
   */
  @Override
  public void setAlcoholic(boolean alcoholic) {

    this.alcoholic = alcoholic;
  }

}
