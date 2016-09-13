package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferSortByHitEntry;
import io.oasp.module.jpa.common.api.to.OrderDirection;

/**
 *
 */
public class OfferSortBy {

  private OfferSortByHitEntry sortByEntry;

  private OrderDirection orderBy;

  /**
   * The constructor.
   */
  public OfferSortBy() {

    this.sortByEntry = OfferSortByHitEntry.ID;
    this.orderBy = OrderDirection.ASC;
  }

  /**
   * Returns the field 'sortByEntry'.
   *
   * @return Value of sortByEntry
   */
  public OfferSortByHitEntry getSortByEntry() {

    return this.sortByEntry;
  }

  /**
   * Sets the field 'sortByEntry'.
   *
   * @param sortByEntry New value for sortByEntry
   */
  public void setSortByEntry(OfferSortByHitEntry sortByEntry) {

    this.sortByEntry = sortByEntry;
  }

  /**
   * Returns the field 'orderBy'.
   *
   * @return Value of orderBy
   */
  public OrderDirection getOrderBy() {

    return this.orderBy;
  }

  /**
   * Sets the field 'orderBy'.
   *
   * @param orderBy New value for orderBy
   */
  public void setOrderBy(OrderDirection orderBy) {

    this.orderBy = orderBy;
  }

}
