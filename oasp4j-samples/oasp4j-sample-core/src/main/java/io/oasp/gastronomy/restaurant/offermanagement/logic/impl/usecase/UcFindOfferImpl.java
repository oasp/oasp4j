package io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferCto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcFindOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcFindProduct;
import io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase.AbstractOfferUc;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

/**
 * Use case implementation for searching, filtering and getting Offers
 */
@Named
@UseCase
@Validated
public class UcFindOfferImpl extends AbstractOfferUc implements UcFindOffer {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcFindOfferImpl.class);

  /** Use case for finding products */
  private UcFindProduct ucFindProduct;

  @Override
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public OfferEto findOffer(Long id) {

    LOG.debug("Get OfferEto with id '{}' from database.", id);
    return getBeanMapper().map(getOfferDao().findOne(id), OfferEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public PaginatedListTo<OfferEto> findOfferEtos(OfferSearchCriteriaTo criteria) {

    criteria.limitMaximumPageSize(MAXIMUM_HIT_LIMIT);
    PaginatedListTo<OfferEntity> offers = getOfferDao().findOffers(criteria);
    return mapPaginatedEntityList(offers, OfferEto.class);
  }

  @Override
  @RolesAllowed({ PermissionConstants.FIND_OFFER, PermissionConstants.FIND_PRODUCT })
  public OfferCto findOfferCto(Long id) {

    LOG.debug("Get OfferCTO with id '{}' from database.", id);
    OfferCto result = new OfferCto();
    // offer
    OfferEto offerEto = findOffer(id);
    if (offerEto == null) {
      return null;
    }
    result.setOffer(offerEto);
    // meal
    Long mealId = offerEto.getMealId();
    if (mealId != null) {
      result.setMeal(this.ucFindProduct.findMeal(mealId));
    }
    // drink
    Long drinkId = offerEto.getDrinkId();
    if (drinkId != null) {
      result.setDrink(this.ucFindProduct.findDrink(drinkId));
    }
    // sideDish
    Long sideDishId = offerEto.getSideDishId();
    if (sideDishId != null) {
      result.setSideDish(this.ucFindProduct.findSideDish(sideDishId));
    }
    return result;
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<OfferEto> findAllOffers() {

    LOG.debug("Get all offers from database.");
    return getBeanMapper().mapList(getOfferDao().findAll(), OfferEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_OFFER)
  public List<OfferEto> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy) {

    List<OfferEntity> offers = getOfferDao().findOffersFiltered(offerFilterBo, sortBy);
    LOG.debug("'" + offers.size() + "' offers fetched.");

    List<OfferEto> offerBos = new ArrayList<>(offers.size());
    for (OfferEntity o : offers) {
      offerBos.add(getBeanMapper().map(o, OfferEto.class));
    }
    return offerBos;
  }

  /**
   * @param ucFindProduct new value of {@link #getucFindProduct}.
   */
  @Inject
  @UseCase
  public void setUcFindProduct(UcFindProduct ucFindProduct) {

    this.ucFindProduct = ucFindProduct;
  }
}
