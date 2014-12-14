package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.SideDish;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity persistent entity} for
 * {@link SideDish}.
 *
 * @author loverbec
 */
@Entity(name = "SideDish")
@DiscriminatorValue("SideDish")
public class SideDishEntity extends ProductEntity implements SideDish {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public SideDishEntity() {

    super();
  }

}
