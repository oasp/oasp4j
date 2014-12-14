package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;

/**
 * Interface of UcMangeOrder to centralize documentation and signatures of methods.
 *
 * @author mvielsac
 */
public interface UcManageOrder {

  /**
   * If no ID is contained creates the {@link OrderCto} for the first time. Else it updates the {@link OrderCto} with
   * given ID. If no {@link OrderCto} with given ID is present, an exception will be thrown.
   *
   * @param order the {@link OrderCto} to persist.
   * @return the persisted {@link OrderCto}.
   */
  OrderCto saveOrder(OrderCto order);

  /**
   * If no ID is contained creates the {@link OrderEto} for the first time. Else it updates the {@link OrderEto} with
   * given ID. If no {@link OrderEto} with given ID is present, an exception will be thrown.
   *
   * @param order the {@link OrderEto} to persist.
   * @return the persisted {@link OrderEto}.
   */
  OrderEto saveOrder(OrderEto order);

  /**
   * Persists new {@link OrderEto} with table ID set to ID of the given {@link TableEto}.
   * 
   * @param table
   * @return The persisted {@link OrderEto}.
   */
  OrderEto saveOrder(TableEto table);

  /**
   * @param id is the {@link OrderEto#getId() ID} of the order to delete.
   */
  void deleteOrder(Long id);
}
