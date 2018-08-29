package io.oasp.module.jpa.dataaccess.api.dao;

import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;

/**
 * This is a simplified variant of {@link IGenericRevisionedDao} for the suggested and common case that you have a
 * {@link Long} as {@link MutablePersistenceEntity#getId() primary key}.
 *
 * @param <ENTITY> is the type of the managed {@link MutablePersistenceEntity entity}.
 */
public interface IRevisionedDao<ENTITY extends MutablePersistenceEntity<Long>> extends
    IGenericRevisionedDao<Long, ENTITY> {

}
