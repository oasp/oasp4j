package io.oasp.module.jpa.dataaccess.base;

import io.oasp.module.jpa.dataaccess.api.RevisionedDao;
import io.oasp.module.jpa.dataaccess.api.RevisionedPersistenceEntity;

/**
 * Abstract base implementation of {@link RevisionedDao} interface.
 *
 * @param <ENTITY> is the type of the managed {@link RevisionedPersistenceEntity entity}.
 * @author hohwille
 */
public abstract class AbstractRevisionedDao<ENTITY extends RevisionedPersistenceEntity<Long>> extends
    AbstractGenericRevisionedDao<Long, ENTITY> implements RevisionedDao<ENTITY> {

}
