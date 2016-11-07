package io.oasp.module.jpa.dataaccess.base;

import io.oasp.module.jpa.dataaccess.api.RevisionedDao;
import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;

/**
 * Abstract base implementation of {@link RevisionedDao} interface.
 *
 * @param <ENTITY> is the type of the managed {@link MutablePersistenceEntity entity}.
 */
public abstract class AbstractRevisionedDao<ENTITY extends MutablePersistenceEntity<Long>> extends
    AbstractGenericRevisionedDao<Long, ENTITY> implements RevisionedDao<ENTITY> {

}
