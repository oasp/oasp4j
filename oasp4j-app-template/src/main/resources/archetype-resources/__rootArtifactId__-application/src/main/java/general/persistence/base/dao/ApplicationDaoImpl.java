#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.persistence.base.dao;

import ${package}.general.persistence.api.dao.ApplicationDao;
import io.oasp.module.jpa.persistence.base.AbstractDao;

import net.sf.mmm.util.entity.api.PersistenceEntity;

import org.springframework.stereotype.Repository;

/**
 * This is the abstract base implementation of {@link ApplicationDao}.
 *
 * @param <ENTITY> is the {@link ${symbol_pound}getEntityClass() type} of the managed entity.
 *
 * @author hohwille
 */
@Repository
public abstract class ApplicationDaoImpl<ENTITY extends PersistenceEntity<Long>> extends AbstractDao<ENTITY> implements
    ApplicationDao<ENTITY> {

  /**
   * The constructor.
   */
  public ApplicationDaoImpl() {

    super();
  }

}
