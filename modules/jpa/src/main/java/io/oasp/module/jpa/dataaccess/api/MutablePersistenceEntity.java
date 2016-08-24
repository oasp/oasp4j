/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.oasp.module.jpa.dataaccess.api;

import net.sf.mmm.util.entity.api.MutableGenericEntity;
import net.sf.mmm.util.entity.api.PersistenceEntity;

/**
 * This is the interface for a {@link PersistenceEntity} in OASP.
 *
 * @param <ID> is the type of the {@link #getId() primary key}.
 *
 * @see AbstractPersistenceEntity
 *
 */
public interface MutablePersistenceEntity<ID> extends PersistenceEntity<ID>, MutableGenericEntity<ID> {

  /**
   * This method sets the {@link #getRevision() revision} of this entity. <br>
   * <b>ATTENTION:</b><br>
   * This operation should only be used in specific cases and if you are aware of what you are doing as this attribute
   * is managed by the persistence. However, for final freedom we decided to add this method to the API (e.g. to copy
   * from transfer-object to persistent-entity and vice-versa).
   *
   * @param revision is the new value of {@link #getRevision()}.
   */
  void setRevision(Number revision);

}
