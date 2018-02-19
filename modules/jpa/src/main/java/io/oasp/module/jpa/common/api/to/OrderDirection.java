package io.oasp.module.jpa.common.api.to;

/**
 * {@link Enum} for sort order.
 *
 * @deprecated has been moved to {@link io.oasp.module.basic.common.api.to.OrderDirection oasp-basic module}. Please use
 *             the new implementation in favor of this unsupported one.
 */
@Deprecated
public enum OrderDirection {

  /** Sort in ascending order. */
  ASC,

  /** Sort in descending order. */
  DESC;

  /**
   * @return {@code true}, if {@link OrderDirection#ASC} is set. {@code false} otherwise.
   */
  public boolean isAsc() {

    return this == ASC;
  }

  /**
   * @return {@code true}, if {@link OrderDirection#DESC} is set. {@code false} otherwise.
   */
  public boolean isDesc() {

    return this == DESC;
  }

}
