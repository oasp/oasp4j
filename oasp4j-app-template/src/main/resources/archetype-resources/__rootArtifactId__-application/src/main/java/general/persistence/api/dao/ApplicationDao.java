#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.persistence.api.dao;

import io.oasp.module.jpa.persistence.api.Dao;

import net.sf.mmm.util.entity.api.PersistenceEntity;

/**
 * Interface for all {@link Dao DAOs} (Data Access Object) of this application.
 *
 * @author etomety
 *
 * @param <ENTITY> is the type of the managed entity.
 */
public interface ApplicationDao<ENTITY extends PersistenceEntity<Long>> extends Dao<ENTITY> {

}
