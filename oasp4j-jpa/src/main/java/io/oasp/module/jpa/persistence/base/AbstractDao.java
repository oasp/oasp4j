package io.oasp.module.jpa.persistence.base;

import io.oasp.module.jpa.persistence.api.AbstractPersistenceEntity;
import io.oasp.module.jpa.persistence.api.Dao;

import net.sf.mmm.util.entity.api.PersistenceEntity;

/**
 * Abstract base implementation of {@link Dao} interface.
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}. Should be derived from
 *        {@link AbstractPersistenceEntity}.
 *
 * @author hohwille
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