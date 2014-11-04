#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.api.to;

import ${package}.general.common.api.datatype.OrderBy;
import ${package}.offermanagement.common.api.datatype.ProductSortByHitEntry;

// TODO mvielsac javadoc for class is missing

/**
 * 
 * @author erandres
 */
public class ProductSortBy {
  private ProductSortByHitEntry sortByEntry;

  private OrderBy orderBy;

  /**
   * Constructor for {@link ProductSortBy}.
   */
  public ProductSortBy() {

    this.sortByEntry = ProductSortByHitEntry.ID;
    this.orderBy = OrderBy.ASC;
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
  public OrderBy getOrderBy() {

    return this.orderBy;
  }

  /**
   * Sets the field 'orderBy'.
   * 
   * @param orderBy New value for orderBy
   */
  public void setOrderBy(OrderBy orderBy) {

    this.orderBy = orderBy;
  }
}
