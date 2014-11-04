#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.base.usecase;

import ${package}.general.logic.base.AbstractUc;
import ${package}.salesmanagement.persistence.api.dao.OrderPositionDao;

import javax.inject.Inject;

/**
 *
 * @author mbrunnli
 */
public abstract class AbstractOrderPositionUc extends AbstractUc {

  /** @see ${symbol_pound}getOrderPositionDao() */
  private OrderPositionDao orderPositionDao;

  /**
   * @return the {@link OrderPositionDao} instance.
   */
  public OrderPositionDao getOrderPositionDao() {

    return this.orderPositionDao;
  }

  /**
   * Sets the field 'orderPositionDao'.
   *
   * @param orderPositionDao New value for orderPositionDao
   */
  @Inject
  public void setOrderPositionDao(OrderPositionDao orderPositionDao) {

    this.orderPositionDao = orderPositionDao;
  }

}
