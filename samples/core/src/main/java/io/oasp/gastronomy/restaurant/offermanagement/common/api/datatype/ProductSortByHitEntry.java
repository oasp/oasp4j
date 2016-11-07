package io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype;

/**
 * This enum identifies the entity, on which the sorting should be executed.
 *
 */
public enum ProductSortByHitEntry {

  // to implement: Type column
  /** Sort by {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product#getId() product id}. */
  ID("id"),

  /**
   * Sort by {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product#getDescription() product
   * description}.
   */
  DESCRIPTION("description"),

  /**
   * Sort by {@link javax.persistence.DiscriminatorValue}
   *
   * @see Object#getClass()
   */
  DTYPE("dtype");

  private String sortByAttributeName;

  private ProductSortByHitEntry(String sortByAttributeName) {

    this.sortByAttributeName = sortByAttributeName;
  }

  /**
   * @return the name of the represented attribute of
   *         {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Product}.
   */
  public String getSortByAttributeName() {

    return this.sortByAttributeName;
  }

  /**
   * This method returns an {@link ProductSortByHitEntry} for a given {@link #getSortByAttributeName() attribute name}.
   *
   * @param sortByAttributeName the name.
   * @return an {@link ProductSortByHitEntry}
   */
  public static ProductSortByHitEntry getEntryForAttributeName(String sortByAttributeName) {

    for (ProductSortByHitEntry entry : ProductSortByHitEntry.values()) {
      if (entry.sortByAttributeName.equals(sortByAttributeName)) {
        return entry;
      }
    }

    return null;
  }
}
