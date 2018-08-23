package io.oasp.module.jpa.dataaccess.base.dao;

import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;
import io.oasp.module.jpa.dataaccess.api.dao.IRevisionedDao;

/**
 * Abstract base implementation of {@link IRevisionedDao} interface.
 *
 * @param <ENTITY> is the type of the managed {@link MutablePersistenceEntity entity}.
 */
public abstract class AbstractRevisionedDaoImpl<ENTITY extends MutablePersistenceEntity<Long>> extends
    AbstractGenericRevisionedDaoImpl<Long, ENTITY> implements IRevisionedDao<ENTITY> {

}
