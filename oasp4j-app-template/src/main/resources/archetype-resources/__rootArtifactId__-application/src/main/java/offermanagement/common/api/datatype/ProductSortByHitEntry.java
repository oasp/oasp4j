#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.common.api.datatype;

/**
 * This enum identifies the entity, on which the sorting should be executed.
 *
 * @author erandres
 */
public enum ProductSortByHitEntry {

  // to implement: Type column
  /** Sort by {@link ${package}.offermanagement.common.api.Product${symbol_pound}getId() product id}. */
  ID("id"),

  /**
   * Sort by {@link ${package}.offermanagement.common.api.Product${symbol_pound}getDescription() product
   * description}.
   */
  DESCRIPTION("description"),

  /**
   * Sort by {@link javax.persistence.DiscriminatorValue} (
   * {@link ${package}.offermanagement.common.api.Product${symbol_pound}getClass()}).
   */
  DTYPE("dtype");

  private String sortByAttributeName;

  private ProductSortByHitEntry(String sortByAttributeName) {

    this.sortByAttributeName = sortByAttributeName;
  }

  /**
   * @return the name of the represented attribute of
   *         {@link ${package}.offermanagement.common.api.Product}.
   */
  public String getSortByAttributeName() {

    return this.sortByAttributeName;
  }

  /**
   * This method returns an {@link OfferSortByHitEntry} for a given {@link ${symbol_pound}getSortByAttributeName() attribute name}.
   *
   * @param sortByAttributeName the name.
   * @return an {@link OfferSortByHitEntry}
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
