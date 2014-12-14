package io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype;

/**
 * This enum identifies the entity, on which the sorting should be executed.
 *
 * @author jozitz
 */
public enum OfferSortByHitEntry {

  ID("id"), DESCRIPTION("description"), PRICE("price"), MEAL("meal"), SIDEDISH("sideDish"), DRINK("drink");

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
