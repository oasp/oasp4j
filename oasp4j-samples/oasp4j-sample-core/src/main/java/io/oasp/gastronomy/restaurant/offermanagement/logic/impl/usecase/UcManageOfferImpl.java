package io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.exception.OfferEmptyException;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcManageOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase.AbstractOfferUc;

import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Named;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

/**
 * Use case implementation for modifying and deleting Offers
 */
@Named
@UseCase
@Validated
public class UcManageOfferImpl extends AbstractOfferUc implements UcManageOffer {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageOfferImpl.class);

  @Override
  @RolesAllowed(PermissionConstants.DELETE_OFFER)
  public void deleteOffer(Long offerId) {

    getOfferDao().delete(offerId);
  }

  @Override
  @RolesAllowed(PermissionConstants.SAVE_OFFER)
  public OfferEto saveOffer(@Valid OfferEto offer) {

    Objects.requireNonNull(offer, "offer");

    if ((offer.getMealId() == null) && (offer.getDrinkId() == null) && (offer.getSideDishId() == null)) {
      throw new OfferEmptyException(offer);
    } else {
      OfferEntity persistedOffer = getOfferDao().save(getBeanMapper().map(offer, OfferEntity.class));
      return getBeanMapper().map(persistedOffer, OfferEto.class);
    }
  }
}
