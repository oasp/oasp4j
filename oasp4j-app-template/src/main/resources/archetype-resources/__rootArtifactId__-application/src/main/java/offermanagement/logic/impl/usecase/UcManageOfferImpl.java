#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.impl.usecase;

import ${package}.offermanagement.common.api.Offer;
import ${package}.offermanagement.common.api.exception.OfferEmptyException;
import ${package}.offermanagement.logic.api.to.OfferEto;
import ${package}.offermanagement.logic.api.usecase.UcManageOffer;
import ${package}.offermanagement.logic.base.usecase.AbstractOfferUc;
import ${package}.offermanagement.persistence.api.OfferEntity;

import java.util.Objects;

import javax.inject.Named;

import net.sf.mmm.util.exception.api.DuplicateObjectException;

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
  public void updateOffer(OfferEto offer) {

    Objects.requireNonNull(offer, "offer");

    OfferEntity persistedOffer = getBeanMapper().map(offer, OfferEntity.class);
    getOfferDao().save(persistedOffer);
    return;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createOffer(OfferEto offer) {

    Objects.requireNonNull(offer, "offer");

    Long offerId = offer.getId();

    OfferEntity persistedOffer = getOfferDao().findOne(offerId);
    if (persistedOffer != null) {
      throw new DuplicateObjectException(Offer.class, offerId);
    }

    if ((offer.getMealId() == null) && (offer.getDrinkId() == null) && (offer.getSideDishId() == null)) {
      throw new OfferEmptyException(offer);
    } else {
      getOfferDao().save(getBeanMapper().map(offer, OfferEntity.class));
      return;
    }

  }

}
