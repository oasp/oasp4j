package io.oasp.gastronomy.restaurant.salesmanagement.logic.base.usecase;

import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderPositionDao;

import javax.inject.Inject;

/**
 *
 */
public abstract class AbstractOrderPositionUc extends AbstractUc {

  /** @see #getOrderPositionDao() */
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
