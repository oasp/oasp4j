package io.oasp.module.jpa.dataaccess.base;

import io.oasp.module.jpa.dataaccess.api.Dao;

import net.sf.mmm.util.entity.api.PersistenceEntity;

/**
 * Abstract base implementation of {@link Dao} interface.
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}. Should be derived from
 *        {@link io.oasp.module.jpa.dataaccess.api.AbstractPersistenceEntity}.
 *
 */
public abstract class AbstractDao<E extends PersistenceEntity<Long>> extends AbstractGenericDao<Long, E> implements
    Dao<E> {

  /**
   * The constructor.
   */
  public AbstractDao() {

    super();
  }

}