#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.impl.dao;

import ${package}.general.persistence.base.dao.ApplicationMasterDataDaoImpl;
import ${package}.offermanagement.persistence.api.DrinkEntity;
import ${package}.offermanagement.persistence.api.dao.DrinkDao;

import javax.inject.Named;

/**
 * Implementation of {@link DrinkDao}.
 *
 * @author hohwille
 */
@Named
public class DrinkDaoImpl extends ApplicationMasterDataDaoImpl<DrinkEntity> implements DrinkDao {

  /**
   * The constructor.
   */
  public DrinkDaoImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<DrinkEntity> getEntityClass() {

    return DrinkEntity.class;
  }

}
