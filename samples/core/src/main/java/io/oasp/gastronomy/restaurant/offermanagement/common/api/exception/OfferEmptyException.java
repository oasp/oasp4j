package io.oasp.gastronomy.restaurant.offermanagement.common.api.exception;

import io.oasp.gastronomy.restaurant.general.common.api.NlsBundleApplicationRoot;
import io.oasp.gastronomy.restaurant.general.common.api.exception.ApplicationBusinessException;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer;

/**
 * This exception is thrown if an {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} is empty. Here
 * empty means that is has no {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getDrinkId() drink},
 * no {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getMealId() meal}, and no
 * {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getSideDishId() side-dish} associated.
 *
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
