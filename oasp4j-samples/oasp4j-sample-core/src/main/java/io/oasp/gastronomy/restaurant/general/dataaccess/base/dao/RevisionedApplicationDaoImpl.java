package io.oasp.gastronomy.restaurant.general.dataaccess.base.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationDao;
import io.oasp.module.jpa.dataaccess.api.AbstractRevisionedDaoEnvers;
import io.oasp.module.jpa.dataaccess.api.RevisionedPersistenceEntity;

import org.springframework.stereotype.Repository;

/**
 * This is the abstract base implementation of {@link ApplicationDao}.
 *
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 *
 * @author hohwille
 */
@Repository
public abstract class RevisionedApplicationDaoImpl<ENTITY extends RevisionedPersistenceEntity<Long>> extends
    AbstractRevisionedDaoEnvers<Long, ENTITY> implements ApplicationDao<ENTITY> {

  /**
   * The constructor.
   */
  public RevisionedApplicationDaoImpl() {

    super();
  }

}
