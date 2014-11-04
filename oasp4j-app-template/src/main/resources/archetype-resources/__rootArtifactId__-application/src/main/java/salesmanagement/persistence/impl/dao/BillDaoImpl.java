#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.persistence.impl.dao;

import ${package}.general.persistence.base.dao.ApplicationDaoImpl;
import ${package}.salesmanagement.persistence.api.BillEntity;
import ${package}.salesmanagement.persistence.api.dao.BillDao;

import javax.inject.Named;

/**
 * Implementation of {@link BillDao}.
 *
 * @author jozitz
 */
@Named
public class BillDaoImpl extends ApplicationDaoImpl<BillEntity> implements BillDao {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<BillEntity> getEntityClass() {

    return BillEntity.class;
  }

}
