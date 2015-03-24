/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.oasp.module.jpa.dataaccess.api;

import io.oasp.module.jpa.dataaccess.base.AbstractGenericDao;
import io.oasp.module.jpa.dataaccess.impl.LazyRevisionMetadata;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.exception.api.ObjectNotFoundException;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

/**
 * This is the abstract base-implementation of a {@link AbstractGenericDao} using {@link org.hibernate.envers
 * Hibernate-Envers} to manage the revision-control.
 *
 * @param <ID> is the type of the primary key of the managed {@link io.oasp.module.jpa.dataaccess.api.GenericDao}.
 * @param <E> is the {@link #getEntityClass() type} of the managed entity.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractRevisionedDao<ID, E extends RevisionedPersistenceEntity<ID>> extends
    AbstractGenericDao<ID, E> implements RevisionedDao<ID, E> {

  /**
   * The constructor.
   */
  public AbstractRevisionedDao() {

    super();
  }

  /**
   * @return the auditReader
   */
  protected AuditReader getAuditReader() {

    return AuditReaderFactory.get(getEntityManager());
  }

  /**
   * {@inheritDoc}
   */
  public E load(ID id, Number revision) throws ObjectNotFoundException {

    if (revision == RevisionedPersistenceEntity.LATEST_REVISION) {
      return find(id);
    } else {
      return loadRevision(id, revision);
    }
  }

  /**
   * This method gets a historic revision of the {@link net.sf.mmm.util.entity.api.GenericEntity} with the given
   * <code>id</code>.
   *
   * @param id is the {@link net.sf.mmm.util.entity.api.GenericEntity#getId() ID} of the requested
   *        {@link net.sf.mmm.util.entity.api.GenericEntity entity}.
   * @param revision is the {@link RevisionedPersistenceEntity#getRevision() revision}
   * @return the requested {@link net.sf.mmm.util.entity.api.GenericEntity entity}.
   */
  protected E loadRevision(Object id, Number revision) {

    Class<? extends E> entityClassImplementation = getEntityClass();
    E entity = getAuditReader().find(entityClassImplementation, id, revision);
    if (entity != null) {
      entity.setRevision(revision);
    }
    return entity;
  }

  // /**
  // * {@inheritDoc}
  // */
  // public Number createRevision(E entity) {
  //
  // // TODO:
  // return null;
  // }

  /**
   * {@inheritDoc}
   */
  public List<Number> getRevisionHistory(ID id) {

    return getAuditReader().getRevisions(getEntityClass(), id);
  }

  /**
   * {@inheritDoc}
   */
  public List<RevisionMetadata> getRevisionHistoryMetadata(Object id) {

    AuditReader auditReader = getAuditReader();
    List<Number> revisionList = auditReader.getRevisions(getEntityClass(), id);
    List<RevisionMetadata> result = new ArrayList<>();
    for (Number revision : revisionList) {
      Long revisionLong = Long.valueOf(revision.longValue());
      result.add(new LazyRevisionMetadata(getEntityManager(), revisionLong));
    }
    return result;
  }
}
