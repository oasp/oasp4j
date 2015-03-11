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
public abstract class AbstractRevisionedDaoEnvers<ID, E extends RevisionedPersistenceEntity<ID>> extends
    AbstractGenericDao<ID, E> {

  /**
   * The constructor.
   */
  public AbstractRevisionedDaoEnvers() {

    super();
  }

  /**
   * @return the auditReader
   */
  protected AuditReader getAuditReader() {

    return AuditReaderFactory.get(getEntityManager());
  }

  /**
   * This method loads a historic {@link RevisionedPersistenceEntity#getRevision() revision} of the
   * {@link RevisionedPersistenceEntity} with the given <code>id</code> from the persistent store. <br>
   * However if the given <code>revision</code> is {@link RevisionedPersistenceEntity#LATEST_REVISION} the
   * {@link #find(Object) latest revision will be loaded}. <br>
   * <b>ATTENTION:</b><br>
   * You should not make assumptions about the <code>revision</code> numbering of the underlying implementation. Please
   * use {@link #getRevisionHistory(RevisionedPersistenceEntity)} or {@link #getRevisionHistoryMetadata(Object)} to find
   * revision numbers.
   *
   * @param id is the {@link RevisionedPersistenceEntity#getId() primary key} of the requested
   *        {@link RevisionedPersistenceEntity entity}.
   * @param revision is the {@link RevisionedPersistenceEntity#getRevision() revision} of the requested entity or
   *        {@link RevisionedPersistenceEntity#LATEST_REVISION} to get the {@link #find(Object) latest} revision. A
   *        specific revision has to be greater than <code>0</code>.
   * @return the requested {@link RevisionedPersistenceEntity entity}.
   * @throws ObjectNotFoundException if the requested {@link RevisionedPersistenceEntity entity} could NOT be found.
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
   * @throws ObjectNotFoundException if the requested {@link net.sf.mmm.util.entity.api.GenericEntity entity} could NOT
   *         be found.
   */
  protected E loadRevision(Object id, Number revision) throws ObjectNotFoundException {

    Class<? extends E> entityClassImplementation = getEntityClass();
    E entity = getAuditReader().find(entityClassImplementation, id, revision);
    if (entity != null) {
      entity.setRevision(revision);
    }
    return entity;
  }

  /**
   * This method creates a new {@link RevisionedPersistenceEntity#getRevision() revision} of the given entity. The given
   * entity is saved and a copy is written to the revision-history
   *
   * @param entity is the entity to create a new revision of.
   * @return the {@link RevisionedPersistenceEntity#getRevision() revision} of the created history-entry.
   */
  public Number createRevision(E entity) {

    // TODO:
    return null;
  }

  /**
   * This method will get the {@link List} of historic {@link RevisionedPersistenceEntity#getRevision() revisions}
   * before the given <code>entity</code>. If the given <code>entity</code> is the
   * {@link RevisionedPersistenceEntity#LATEST_REVISION latest revision} ... <br>
   * If the <code>entity</code> is NOT revision controlled, an {@link java.util.Collections#emptyList() empty list} is
   * returned.
   *
   * @param entity is the according {@link RevisionedPersistenceEntity}.
   * @return the {@link List} of historic {@link RevisionedPersistenceEntity#getRevision() revisions}.
   */
  public List<Number> getRevisionHistory(E entity) {

    return getAuditReader().getRevisions(getEntityClass(), entity.getId());
  }

  /**
   * This method will get the {@link List} of {@link RevisionMetadata} from the
   * {@link RevisionedPersistenceEntity#getRevision() revision}-history of the {@link #getEntityClass() entity} with the
   * given <code>id</code>.
   *
   * @param id is the {@link RevisionedPersistenceEntity#getId() primary key} of the entity for which the
   *        history-metadata is requested.
   * @return the requested {@link List} of {@link RevisionMetadata}.
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
