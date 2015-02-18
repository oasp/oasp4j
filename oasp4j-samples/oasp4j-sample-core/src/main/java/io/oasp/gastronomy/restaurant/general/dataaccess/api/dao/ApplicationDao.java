package io.oasp.gastronomy.restaurant.general.dataaccess.api.dao;

import io.oasp.module.jpa.dataaccess.api.RevisionedDao;
import io.oasp.module.jpa.dataaccess.api.RevisionedPersistenceEntity;

/**
 * Interface for all {@link RevisionedDao DAOs} (Data Access Object) of this application.
 *
 * @author etomety
 *
 * @param <ENTITY> is the type of the managed entity.
 */
public interface ApplicationDao<ENTITY extends RevisionedPersistenceEntity<Long>> extends RevisionedDao<Long, ENTITY> {

}
