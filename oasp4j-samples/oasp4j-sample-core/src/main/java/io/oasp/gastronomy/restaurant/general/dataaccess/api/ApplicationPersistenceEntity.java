package io.oasp.gastronomy.restaurant.general.dataaccess.api;

import io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity;
import io.oasp.module.jpa.dataaccess.api.AbstractPersistenceEntity;

import javax.persistence.MappedSuperclass;

/**
 * Abstract Entity for all Entities with an id and a version field.
 *
 * @author hohwille
 * @author rjoeris
 */
@MappedSuperclass
public abstract class ApplicationPersistenceEntity extends AbstractPersistenceEntity implements ApplicationEntity {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public ApplicationPersistenceEntity() {

    super();
  }

}
