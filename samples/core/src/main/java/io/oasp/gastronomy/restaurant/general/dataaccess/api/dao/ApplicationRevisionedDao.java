package io.oasp.gastronomy.restaurant.general.dataaccess.api.dao;

import io.oasp.module.jpa.dataaccess.api.GenericRevisionedDao;
import io.oasp.module.jpa.dataaccess.api.RevisionedDao;
import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;

/**
 * Interface for all {@link GenericRevisionedDao DAOs} (Data Access Object) of this application.
 *
 *
 * @param <ENTITY> is the type of the managed entity.
 */
public interface ApplicationRevisionedDao<ENTITY extends MutablePersistenceEntity<Long>> extends
    ApplicationDao<ENTITY>, RevisionedDao<ENTITY> {

}
