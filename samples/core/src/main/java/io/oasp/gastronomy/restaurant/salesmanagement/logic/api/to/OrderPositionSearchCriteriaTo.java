package io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.module.jpa.common.api.to.SearchCriteriaTo;

/**
 * This is the {@link SearchCriteriaTo search criteria} {@link net.sf.mmm.util.transferobject.api.TransferObject TO}
 * used to find {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition}s.
 *
 */
public class OrderPositionSearchCriteriaTo extends SearchCriteriaTo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private OrderPositionState state;

  private ProductOrderState drinkState;

  private Long orderId;

  private Long cookId;

  private boolean mealOrSideDish;

  /**
   * The constructor.
   */
  public OrderPositionSearchCriteriaTo() {

    super();
  }

  /**
   * @return orderPositionState
   */
  public OrderPositionState getState() {

    return this.state;
  }

  /**
   * @param orderPositionState the orderPositionState to set
   */
  public void setState(OrderPositionState orderPositionState) {

    this.state = orderPositionState;
  }

  /**
   * @return drinkState of the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition }
   */
  public ProductOrderState getDrinkState() {

    return this.drinkState;
  }

  /**
   * @param drinkState the drinkState to set
   */
  public void setDrinkState(ProductOrderState drinkState) {

    this.drinkState = drinkState;
  }

  /**
   * @return the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition#getOrderId() order ID} of
   *         the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition}s to find or {@code null}
   *         if no such criteria shall be added.
   */
  public Long getOrderId() {

    return this.orderId;
  }

  /**
   * @param orderId the orderId to set
   */
  public void setOrderId(Long orderId) {

    this.orderId = orderId;
  }

  /**
   * @return the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition#getCookId() cook ID} of
   *         the {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition}s to find or {@code null}
   *         if no such criteria shall be added.
   */
  public Long getCookId() {

    return this.cookId;
  }

  /**
   * @param cookId the cookId to set
   */
  public void setCookId(Long cookId) {

    this.cookId = cookId;
  }

  /**
   * @return {@code true} if only {@link io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition}s shall
   *         be found that have a meal or a side-dish associated (exclude drink only positions), {@code false} if no
   *         such criteria shall be added.
   */
  public boolean isMealOrSideDish() {

    return this.mealOrSideDish;
  }

  /**
   * @param mealOrSideDish the mealOrSideDish to set
   */
  public void setMealOrSideDish(boolean mealOrSideDish) {

    this.mealOrSideDish = mealOrSideDish;
  }

}
