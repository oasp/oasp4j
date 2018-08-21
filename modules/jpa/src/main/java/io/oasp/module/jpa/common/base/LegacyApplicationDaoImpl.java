package io.oasp.module.jpa.common.base;

import org.springframework.stereotype.Repository;

import io.oasp.module.jpa.common.api.ApplicationDao;
import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;

/**
 * This is the abstract base implementation of {@link ApplicationDao}.
 *
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 *
 */
@Repository
public abstract class LegacyApplicationDaoImpl<ENTITY extends MutablePersistenceEntity<Long>>
    extends AbstractRevisionedDao<ENTITY> implements ApplicationDao<ENTITY> {

  /**
   * The constructor.
   */
  public LegacyApplicationDaoImpl() {

    super();
  }

}