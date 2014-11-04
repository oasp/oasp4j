#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.common.api.exception;

import ${package}.general.common.api.NlsBundleApplicationRoot;
import ${package}.general.common.api.exception.ApplicationBusinessException;
import ${package}.offermanagement.common.api.Offer;

/**
 * This exception is thrown if an {@link ${package}.offermanagement.common.api.Offer} is empty. Here
 * empty means that is has no {@link ${package}.offermanagement.common.api.Offer${symbol_pound}getDrinkId() drink},
 * no {@link ${package}.offermanagement.common.api.Offer${symbol_pound}getMealId() meal}, and no
 * {@link ${package}.offermanagement.common.api.Offer${symbol_pound}getSideDishId() side-dish} associated.
 *
 * @author hohwille
 */
public class OfferEmptyException extends ApplicationBusinessException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param offer is the empty {@link Offer}.
   */
  public OfferEmptyException(Offer offer) {

    super(createBundle(NlsBundleApplicationRoot.class).errorOfferEmpty());
  }

}
