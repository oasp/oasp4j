package io.oasp.gastronomy.restaurant.salesmanagement.logic.base.usecase;

import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderDao;

import javax.inject.Inject;

/**
 *
 */
public abstract class AbstractOrderUc extends AbstractUc {

  /** @see #getOrderDao() */
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
