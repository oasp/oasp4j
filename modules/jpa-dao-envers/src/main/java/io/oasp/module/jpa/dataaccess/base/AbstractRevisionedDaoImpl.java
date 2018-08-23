package io.oasp.module.jpa.dataaccess.base;

import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;
import io.oasp.module.jpa.dataaccess.api.RevisionedDao;

/**
 * Abstract base implementation of {@link RevisionedDao} interface.
 *
 * @param <ENTITY> is the type of the managed {@link MutablePersistenceEntity entity}.
 */
public abstract class AbstractRevisionedDaoImpl<ENTITY extends MutablePersistenceEntity<Long>>
    extends AbstractGenericRevisionedDaoImpl<Long, ENTITY> implements RevisionedDao<ENTITY> {

}
