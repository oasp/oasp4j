package io.oasp.module.jpa.dataaccess.base;

import net.sf.mmm.util.entity.api.PersistenceEntity;

import io.oasp.module.jpa.dataaccess.api.Dao;

/**
 * Abstract base implementation of {@link Dao} interface.
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}.
 *
 */
public abstract class AbstractDaoImpl<E extends PersistenceEntity<Long>> extends AbstractGenericDaoImpl<Long, E>
    implements Dao<E> {

  /**
   * The constructor.
   */
  public AbstractDaoImpl() {

    super();
  }

}