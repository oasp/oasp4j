package io.oasp.module.jpa.common.base;

import net.sf.mmm.util.entity.api.PersistenceEntity;

import io.oasp.module.jpa.dataaccess.api.Dao;

/**
 * Abstract base implementation of {@link Dao} interface.
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}. Should be derived from
 *        {@link io.oasp.module.jpa.dataaccess.api.AbstractPersistenceEntity}.
 *
 * @deprecated It is only provided to support old functionality of DAO, instead use
 *             io.oasp.module.jpa.dataaccess.base.AbstractDao instead
 */
@Deprecated
public abstract class AbstractDao<E extends PersistenceEntity<Long>> extends AbstractGenericDao<Long, E>
    implements Dao<E> {

  /**
   * The constructor.
   */
  public AbstractDao() {

    super();
  }

}