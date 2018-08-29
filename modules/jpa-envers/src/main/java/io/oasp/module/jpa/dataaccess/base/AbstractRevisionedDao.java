package io.oasp.module.jpa.dataaccess.base;

import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;
import io.oasp.module.jpa.dataaccess.api.RevisionedDao;

/**
 * Abstract base implementation of {@link RevisionedDao} interface.
 *
 * @param <ENTITY> is the type of the managed {@link MutablePersistenceEntity entity}.
 */
@Deprecated
public abstract class AbstractRevisionedDao<ENTITY extends MutablePersistenceEntity<Long>>
    extends AbstractGenericRevisionedDao<Long, ENTITY> implements RevisionedDao<ENTITY> {

}
