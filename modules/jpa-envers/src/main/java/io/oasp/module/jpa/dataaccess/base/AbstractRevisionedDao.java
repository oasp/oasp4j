package io.oasp.module.jpa.dataaccess.base;

import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;
import io.oasp.module.jpa.dataaccess.api.RevisionedDao;

/**
 * Abstract base implementation of {@link RevisionedDao} interface.
 *
 * @param <ENTITY> is the type of the managed {@link MutablePersistenceEntity entity}.
 * @deprecated use {@link AbstractRevisionedDaoImpl} instead. This class only contains the legacy support for searching
 *             and paging.
 */
@Deprecated
public abstract class AbstractRevisionedDao<ENTITY extends MutablePersistenceEntity<Long>>
    extends AbstractGenericRevisionedDao<Long, ENTITY> implements RevisionedDao<ENTITY> {

}
