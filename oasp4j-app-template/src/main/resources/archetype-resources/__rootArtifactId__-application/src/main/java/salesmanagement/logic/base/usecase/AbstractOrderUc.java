#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.base.usecase;

import ${package}.general.logic.base.AbstractUc;
import ${package}.salesmanagement.persistence.api.dao.OrderDao;

import javax.inject.Inject;

/**
 *
 * @author mbrunnli
 */
public abstract class AbstractOrderUc extends AbstractUc {

  /** @see ${symbol_pound}getOrderDao() */
  private OrderDao orderDao;

  /**
   * @return orderDao
   */
  public OrderDao getOrderDao() {

    return this.orderDao;
  }

  /**
   * Sets the field 'orderPositionDao'.
   *
   * @param orderDao New value for orderDao
   */
  @Inject
  public void setOrderDao(OrderDao orderDao) {

    this.orderDao = orderDao;
  }

}
