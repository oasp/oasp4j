package org.oasp.module.entity.common.api;

import java.io.Serializable;

import javax.persistence.Version;

import org.oasp.module.entity.persistence.api.JpaEntity;

/**
 * This is the interface for an entity.
 *
 * @see JpaEntity
 *
 * @author hohwille
 */
public interface Entity extends Serializable {

  /**
   * @return the unique ID of this entity.
   */
  Long getId();

  /**
   * @return the {@link javax.persistence.Version} used for optimistic locking. Incremented on transaction commit with
   *         every modification of the entity.
   */
  @Version
  int getVersion();

}
