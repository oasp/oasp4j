package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.SideDish;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.envers.Audited;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity persistent entity} for
 * {@link SideDish}.
 *
 */
@Entity
@DiscriminatorValue("SideDish")
@Audited
public class SideDishEntity extends ProductEntity implements SideDish {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public SideDishEntity() {

    super();
  }

}
