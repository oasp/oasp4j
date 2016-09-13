package io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype;

/**
 * This enum identifies the entity, on which the sorting should be executed.
 *
 */
public enum OfferSortByHitEntry {

  /**
   * Sort by id.
   */
  ID("id"),
  /**
   * Sort by description.
   */
  DESCRIPTION("description"),
  /**
   * Sort by price.
   */
  PRICE("price"),
  /**
   * Sort by meal.
   */
  MEAL("meal"),
  /**
   * Sort by side dish.
   */
  SIDEDISH("sideDish"),
  /**
   * Sort by drink.
   */
  DRINK("drink");

  private final String sortByAttributeName;

  private OfferSortByHitEntry(String sortByAttributeName) {

    this.sortByAttributeName = sortByAttributeName;
  }

  /**
   * @return sortByAttributeName
   */
  public String getSortByAttributeName() {

    return this.sortByAttributeName;
  }

  /**
   * This method returns an {@link OfferSortByHitEntry} for a given {@link #getSortByAttributeName() attribute name}.
   *
   * @param sortByAttributeName the name.
   * @return an {@link OfferSortByHitEntry}
   */
  public static OfferSortByHitEntry getEntryForAttributeName(String sortByAttributeName) {

    for (OfferSortByHitEntry entry : OfferSortByHitEntry.values()) {
      if (entry.sortByAttributeName.equals(sortByAttributeName)) {
        return entry;
      }
    }

    return null;
  }
}
