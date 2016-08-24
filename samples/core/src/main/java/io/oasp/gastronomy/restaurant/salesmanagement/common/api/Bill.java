package io.oasp.gastronomy.restaurant.salesmanagement.common.api;

import io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.validation.NotNegativeMoney;

import java.util.List;

/**
 * This is the interface for a {@link Bill}. It represents the actual payment for an {@link Order}.
 *
 */
public interface Bill extends ApplicationEntity {

  /**
   * @return the total amount that has to be payed for this {@link Bill}.
   */
  Money getTotal();

  /**
   * @param total is the new {@link #getTotal() total amount}.
   */
  void setTotal(Money total);

  /**
   * @return the tip (voluntary payment in addition to {@link #getTotal() total amount}).
   */
  @NotNegativeMoney(message = "The tip must not be negative!")
  Money getTip();

  /**
   * @param tip is the new {@link #getTip() tip}.
   */
  void setTip(Money tip);

  /**
   * @return {@code true} if this {@link Bill} has been payed, {@code false} otherwise.
   */
  boolean isPayed();

  /**
   * @param payed is the new value of {@link #isPayed() payed}.
   */
  void setPayed(boolean payed);

  /**
   * @return the {@link List} with the {@link OrderPosition#getId() IDs} of the {@link OrderPosition}s.
   */
  List<Long> getOrderPositionIds();

  /**
   * @param ids are the new {@link #getOrderPositionIds() orderPositionIds}.
   */
  void setOrderPositionIds(List<Long> ids);

}
