package io.oasp.module.jpa.dataaccess.api.dao;

import net.sf.mmm.util.entity.api.PersistenceEntity;

/**
 * This is a simplified variant of {@link IGenericDao} for the suggested and common case that you have a {@link Long} as
 * {@link PersistenceEntity#getId() primary key}.
 *
 * @see IGenericDao
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}.
 */
public interface IDao<E extends PersistenceEntity<Long>> extends IGenericDao<Long, E> {

}
