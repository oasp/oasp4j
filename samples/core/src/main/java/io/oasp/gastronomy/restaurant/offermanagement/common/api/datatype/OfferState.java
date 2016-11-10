package io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype;

/**
 * Represents the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getState() state} of an
 * {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer}.
 *
 */
public enum OfferState {

  /** The state of a normal {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer}. */
  NORMAL,

  /** The state of a special {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer}. */
  SPECIAL,

  /** The state of a {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} that is soled-out. */
  SOLDOUT;

  /**
   * @return {@code true} if the
   *         {@link io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity} is normal.
   */
  public boolean isNormal() {

    return (this == NORMAL);
  }

  /**
   * @return {@code true} if the
   *         {@link io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity} is special.
   */
  public boolean isSpecial() {

    return (this == SPECIAL);
  }

  /**
   * @return {@code true} if the
   *         {@link io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity} is sold out.
   */
  public boolean isSoldout() {

    return (this == SOLDOUT);
  }
}
