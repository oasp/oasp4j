package io.oasp.module.jpa.dataaccess.api;

import net.sf.mmm.util.entity.api.PersistenceEntity;

/**
 * This is a simplified variant of {@link GenericDao} for the suggested and common case that you have a {@link Long} as
 * {@link PersistenceEntity#getId() primary key}.
 *
 * @see GenericDao
 * @see AbstractPersistenceEntity
 *
 * @param <E> is the generic type of the {@link PersistenceEntity}.
 *
 * @author hohwille
 */
public interface Dao<E extends PersistenceEntity<Long>> extends GenericDao<Long, E> {

}
