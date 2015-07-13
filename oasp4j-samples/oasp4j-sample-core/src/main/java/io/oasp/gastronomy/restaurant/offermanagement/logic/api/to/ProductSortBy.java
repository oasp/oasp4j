package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.ProductSortByHitEntry;
import io.oasp.module.jpa.common.api.to.OrderDirection;

// TODO mvielsac javadoc for class is missing

/**
 *
 * @author erandres
 */
public class ProductSortBy {
  private ProductSortByHitEntry sortByEntry;

  private OrderDirection orderBy;

  /**
   * Constructor for {@link ProductSortBy}.
   */
  public ProductSortBy() {

    this.sortByEntry = ProductSortByHitEntry.ID;
    this.orderBy = OrderDirection.ASC;
  }

  /**
   * Returns the field 'sortByEntry'.
   *
   * @return Value of sortByEntry
   */
  public ProductSortByHitEntry getSortByEntry() {

    return this.sortByEntry;
  }

  /**
   * Sets the field 'sortByEntry'.
   *
   * @param sortByEntry New value for sortByEntry
   */
  public void setSortByEntry(ProductSortByHitEntry sortByEntry) {

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
