/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.oasp.module.jpa.dataaccess.api;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.entity.api.RevisionedEntity;

/**
 * This is the interface for a mutable {@link RevisionedEntity}.
 *
 * @param <ID> is the type of the {@link #getId() primary key}.
 *
 * @author hohwille
 * @since 3.1.0
 */
public interface RevisionedPersistenceEntity<ID> extends PersistenceEntity<ID> {

  /**
   * The latest {@link #getRevision() revision} of an {@link PersistenceEntity entity}.
   */
  Number LATEST_REVISION = null;

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

  /**
   * This method gets the revision of this entity. The {@link RevisionedEntity#LATEST_REVISION latest revision} of an
   * entity will always return <code>null</code>. Otherwise this object is a <em>historic entity</em> and it will be
   * read-only so modifications are NOT permitted.
   *
   * @return the revision or {@link #LATEST_REVISION} (<code>null</code>) if this is the latest revision.
   */
  Number getRevision();

}
