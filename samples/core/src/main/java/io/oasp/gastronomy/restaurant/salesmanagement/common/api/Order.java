package io.oasp.gastronomy.restaurant.salesmanagement.common.api;

import javax.validation.constraints.NotNull;

import io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;

/**
 * This is the interface for an {@link Order}. It is {@link #getTableId() associated} with a
 * {@link io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table} and consists of multiple {@link OrderPosition
 * positions}.
 *
 */
public interface Order extends ApplicationEntity {

  /**
   * @return the {@link io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table#getId() ID} of the associated
   *         {@link io.oasp.gastronomy.restaurant.tablemanagement.common.api.Table} where this {@link Order} was
   *         disposed.
   */
  @NotNull
  long getTableId();

  /**
   * @param tableId the new {@link #getTableId() tableId}.
   */
  void setTableId(long tableId);

  /**
   * @return the {@link OrderState state} of this {@link Order}.
   */
  @NotNull
  OrderState getState();

  /**
   * @param state is the new {@link #getState() state}.
   */
  void setState(OrderState state);
}
