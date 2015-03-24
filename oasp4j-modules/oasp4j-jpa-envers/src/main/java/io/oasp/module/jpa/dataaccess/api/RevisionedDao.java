package io.oasp.module.jpa.dataaccess.api;

/**
 * This is a simplified variant of {@link GenericRevisionedDao} for the suggested and common case that you have a
 * {@link Long} as {@link RevisionedPersistenceEntity#getId() primary key}.
 *
 * @param <ENTITY> is the type of the managed {@link RevisionedPersistenceEntity entity}.
 * @author hohwille
 */
public interface RevisionedDao<ENTITY extends RevisionedPersistenceEntity<Long>> extends
    GenericRevisionedDao<Long, ENTITY> {

}
