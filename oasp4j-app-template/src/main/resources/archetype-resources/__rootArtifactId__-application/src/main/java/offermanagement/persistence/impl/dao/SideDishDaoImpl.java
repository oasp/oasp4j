#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.impl.dao;

import ${package}.general.persistence.base.dao.ApplicationMasterDataDaoImpl;
import ${package}.offermanagement.persistence.api.SideDishEntity;
import ${package}.offermanagement.persistence.api.dao.SideDishDao;

import javax.inject.Named;

/**
 * Implementation of {@link SideDishDao}.
 *
 * @author hohwille
 */
@Named
public class SideDishDaoImpl extends ApplicationMasterDataDaoImpl<SideDishEntity> implements SideDishDao {

  /**
   * The constructor.
   */
  public SideDishDaoImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<SideDishEntity> getEntityClass() {

    return SideDishEntity.class;
  }

}
