package io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.exception.OfferEmptyException;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase.AbstractOfferUc;

import java.util.Objects;

import javax.inject.Named;

/**
 * Implementation of {@link UcManageOffer}.
 *
 * @author loverbec
 */
@Named
public class UcManageOfferImpl extends AbstractOfferUc implements UcManageOffer {

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteOffer(Long offerId) {

    getOfferDao().delete(offerId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OfferEto saveOffer(OfferEto offer) {

    Objects.requireNonNull(offer, "offer");

    if ((offer.getMealId() == null) && (offer.getDrinkId() == null) && (offer.getSideDishId() == null)) {
      throw new OfferEmptyException(offer);
    } else {
      OfferEntity persistedOffer = getOfferDao().save(getBeanMapper().map(offer, OfferEntity.class));
      return getBeanMapper().map(persistedOffer, OfferEto.class);
    }
  }

}
