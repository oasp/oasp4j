package io.oasp.module.jpa.common.api;

import io.oasp.module.jpa.dataaccess.api.Dao;
import io.oasp.module.jpa.dataaccess.api.GenericRevisionedDao;
import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;

/**
 * Interface for all {@link GenericRevisionedDao DAOs} (Data Access Object) of this application.
 *
 * @param <ENTITY> is the type of the managed entity.
 */
public interface ApplicationDao<ENTITY extends MutablePersistenceEntity<Long>> extends Dao<ENTITY> {

}
