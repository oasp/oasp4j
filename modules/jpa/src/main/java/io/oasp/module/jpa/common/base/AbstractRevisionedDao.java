package io.oasp.module.jpa.common.base;

import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;
import io.oasp.module.jpa.dataaccess.api.RevisionedDao;

/**
 * Abstract base implementation of {@link RevisionedDao} interface.
 *
 * @param <ENTITY> is the type of the managed {@link MutablePersistenceEntity entity}.
 *
 * @deprecated It is only provided to support old functionality of DAO, instead use
 *             io.oasp.module.jpa.dataaccess.base.AbstractRevisionedDao instead
 */
@Deprecated
public abstract class AbstractRevisionedDao<ENTITY extends MutablePersistenceEntity<Long>>
    extends AbstractGenericRevisionedDao<Long, ENTITY> implements RevisionedDao<ENTITY> {

}
