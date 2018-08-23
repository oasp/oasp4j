package io.oasp.module.jpa.dataaccess.base.dao;

import net.sf.mmm.util.entity.api.PersistenceEntity;

import io.oasp.module.jpa.dataaccess.api.dao.IDao;

/**
 * Abstract base implementation of {@link IDao} interface.
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}.
 *
 */
public abstract class AbstractDaoImpl<E extends PersistenceEntity<Long>> extends AbstractGenericDaoImpl<Long, E>
    implements IDao<E> {

  /**
   * The constructor.
   */
  public AbstractDaoImpl() {

    super();
  }

}