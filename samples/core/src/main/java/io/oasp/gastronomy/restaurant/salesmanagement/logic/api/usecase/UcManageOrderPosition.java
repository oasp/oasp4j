package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase;

import javax.validation.Valid;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;

/**
 * Interface of UcManageOrderPosition to centralize documentation and signatures of methods.
 *
 */
public interface UcManageOrderPosition {

  /**
   * Creates an {@link OrderPositionEto} for the given {@link OfferEto} and a given {@link OrderEto}.
   *
   * @param offer is the {@link OfferEto} to be ordered.
   * @param order is the {@link OrderEto order} for which the {@link OrderPositionEto}s are
   * @param comment is the comment for special orders
   *
   * @return the new {@link OrderPositionEto}
   */
  OrderPositionEto createOrderPosition(OfferEto offer, OrderEto order, String comment);

  /**
   * Saves or updates the given {@link OrderPositionEto}.
   *
   * @param orderPosition is the {@link OrderPositionEto} to persist.
   * @return the saved {@link OrderPositionEto}.
   */
  OrderPositionEto saveOrderPosition(@Valid OrderPositionEto orderPosition);

}
