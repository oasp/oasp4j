package io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

import javax.validation.Valid;

/**
 * Interface of UcManageOffer to centralize documentation and signatures of methods.
 */
public interface UcManageOffer {

  /**
   * Deletes an {@link OfferEto} by its {@link OfferEto#getId() id}.
   *
   * @param offerId is the {@link OfferEto#getId() id} that identifies the {@link OfferEto} to be deleted.
   */
  void deleteOffer(Long offerId);

  /**
   * If no ID is contained creates the {@link OfferEto} for the first time. Else it updates the {@link OfferEto} with
   * given ID. If no {@link OfferEto} with given ID is present, an exception will be thrown.
   *
   * @param offer the {@link OfferEto} to persist.
   * @return the generated/updated offer
   */
  OfferEto saveOffer(@Valid OfferEto offer);

}
