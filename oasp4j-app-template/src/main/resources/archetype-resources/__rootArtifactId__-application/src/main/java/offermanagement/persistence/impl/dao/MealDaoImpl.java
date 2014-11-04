#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.impl.dao;

import ${package}.general.persistence.base.dao.ApplicationMasterDataDaoImpl;
import ${package}.offermanagement.persistence.api.MealEntity;
import ${package}.offermanagement.persistence.api.dao.MealDao;

import javax.inject.Named;

/**
 * Implementation of {@link MealDao}.
 *
 * @author hohwille
 */
@Named
public class MealDaoImpl extends ApplicationMasterDataDaoImpl<MealEntity> implements MealDao {

  /**
   * The constructor.
   */
  public MealDaoImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<MealEntity> getEntityClass() {

    return MealEntity.class;
  }

}
