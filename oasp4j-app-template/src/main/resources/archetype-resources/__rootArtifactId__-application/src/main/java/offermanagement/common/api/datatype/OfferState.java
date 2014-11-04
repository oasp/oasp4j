#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.common.api.datatype;

/**
 * Represents the {@link ${package}.offermanagement.common.api.Offer${symbol_pound}getState() state} of an
 * {@link ${package}.offermanagement.common.api.Offer}.
 *
 * @author etomety
 */
public enum OfferState {

  /** The state of a normal {@link ${package}.offermanagement.common.api.Offer}. */
  NORMAL,

  /** The state of a special {@link ${package}.offermanagement.common.api.Offer}. */
  SPECIAL,

  /** The state of a {@link ${package}.offermanagement.common.api.Offer} that is soled-out. */
  SOLDOUT;

  /**
   * @return <code>true</code> if the
   *         {@link ${package}.offermanagement.persistence.api.OfferEntity} is normal.
   */
  public boolean isNormal() {

    return (this == NORMAL);
  }

  /**
   * @return <code>true</code> if the
   *         {@link ${package}.offermanagement.persistence.api.OfferEntity} is special.
   */
  public boolean isSpecial() {

    return (this == SPECIAL);
  }

  /**
   * @return <code>true</code> if the
   *         {@link ${package}.offermanagement.persistence.api.OfferEntity} is sold out.
   */
  public boolean isSoldout() {

    return (this == SOLDOUT);
  }
}
