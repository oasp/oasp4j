package io.oasp.gastronomy.restaurant.general.common.api.datatype;

/**
 * {@link Enum} for sort order.
 *
 * @author jozitz
 */
public enum OrderBy {

  /** Sort in ascending order. */
  ASC,

  /** Sort in descending order. */
  DESC;

  /**
   * @return {@code true}, if {@link OrderBy#ASC} is set. {@code false} otherwise.
   */
  public boolean isAsc() {

    return this == ASC;
  }

  /**
   * @return {@code true}, if {@link OrderBy#DESC} is set. {@code false} otherwise.
   */
  public boolean isDesc() {

    return this == DESC;
  }

}
