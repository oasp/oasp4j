#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.persistence.base.dao;

import io.oasp.module.jpa.persistence.api.MasterDataDao;

import java.util.List;

import net.sf.mmm.util.entity.api.PersistenceEntity;

/**
 * This is the abstract base implemetation of {@link MasterDataDao} based on {@link ApplicationDaoImpl}.
 *
 * @param <ENTITY> is the {@link ${symbol_pound}getEntityClass() type} of the managed entity.
 *
 * @author hohwille
 */
public abstract class ApplicationMasterDataDaoImpl<ENTITY extends PersistenceEntity<Long>> extends
    ApplicationDaoImpl<ENTITY> implements MasterDataDao<ENTITY> {

  /**
   * The constructor.
   */
  public ApplicationMasterDataDaoImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ENTITY> findAll() {

    return super.findAll();
  }

}
