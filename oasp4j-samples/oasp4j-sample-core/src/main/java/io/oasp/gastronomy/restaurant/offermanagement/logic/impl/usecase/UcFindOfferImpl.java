package io.oasp.gastronomy.restaurant.offermanagement.logic.impl.usecase;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.ProductType;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.DrinkEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.MealEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferCto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SideDishEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase.UcFindOffer;
import io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase.AbstractOfferUc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcFindOffer}.
 *
 * @author jozitz
 */
@Named
public class UcFindOfferImpl extends AbstractOfferUc implements UcFindOffer {

  private static final Logger LOG = LoggerFactory.getLogger(UcFindOfferImpl.class);

  private Offermanagement offerManagement;

  /**
   * The constructor.
   */
  public UcFindOfferImpl() {

    super();
  }

  /**
   * @param offerManagement the {@link Offermanagement} to {@link Inject}.
   */
  @Inject
  public void setOfferManagement(Offermanagement offerManagement) {

    this.offerManagement = offerManagement;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OfferEto findOffer(Long id) {

    LOG.debug("Get OfferEto with id '{}' from database.", id);
    return getBeanMapper().map(getOfferDao().findOne(id), OfferEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
      result.setMeal(this.offerManagement.findMeal(mealId));
    }
    // drink
    Long drinkId = offerEto.getDrinkId();
    if (drinkId != null) {
      result.setDrink(this.offerManagement.findDrink(drinkId));
    }
    // sideDish
    Long sideDishId = offerEto.getSideDishId();
    if (sideDishId != null) {
      result.setSideDish(this.offerManagement.findSideDish(sideDishId));
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OfferEto> findAllOffers() {

    LOG.debug("Get all offers from database.");
    return getBeanMapper().mapList(getOfferDao().findAll(), OfferEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isProductInUseByOffer(ProductEto product) {

    LOG.debug("Get all offers from database for the given product with id '" + product.getId() + "'.");

    List<OfferEto> persistedOffers = findAllOffers();

    /*
     * Check the occurrence of a product within all offers. Therefore, only check for a instance of a product type
     * product type.
     */
    ProductType productType = null;

    if (product instanceof DrinkEto) {
      LOG.debug("The given product is an instance of Drink '" + product.getDescription() + "', id '" + product.getId()
          + "'. Check all Offer-Drinks for that given occurrence.");
      productType = ProductType.DRINK;
    } else if (product instanceof MealEto) {
      LOG.debug("The given product is an instance of Meal '" + product.getDescription() + "', id '" + product.getId()
          + "'. Check all Offer-Meals for that given occurrence.");
      productType = ProductType.MEAL;
    } else if (product instanceof SideDishEto) {
      LOG.debug("The given product is an instance of SideDish '" + product.getDescription() + "', id '"
          + product.getId() + "'. Check all Offer-SideDishes for that given occurrence.");
      productType = ProductType.SIDEDISH;
    }

    if (productType == null) {
      LOG.debug("The given product not in use by an offer.");
      return false;
    }

    for (OfferEto offer : persistedOffers) {

      if (productType.isDrink()) {
        if (Objects.equals(offer.getDrinkId(), product.getId())) {
          LOG.debug("The given product is in use by offer with id '" + offer.getId() + "', description '"
              + offer.getDescription() + "'.");
          return true;
        }
        continue;

      }

      if (productType.isMeal()) {
        if (Objects.equals(offer.getMealId(), product.getId())) {
          LOG.debug("The given product is in use by offer with id '" + offer.getId() + "', description '"
              + offer.getDescription() + "'.");
          return true;
        }
        continue;
      }

      if (productType.isSideDish()) {
        if (Objects.equals(offer.getSideDishId(), product.getId())) {
          LOG.debug("The given product is in use by offer with id '" + offer.getId() + "', description '"
              + offer.getDescription() + "'.");
          return true;
        }
        continue;
      }
    }

    LOG.debug("The given product not in use by an offer.");
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OfferEto> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy) {

    List<OfferEntity> offers = getOfferDao().findOffersFiltered(offerFilterBo, sortBy);
    LOG.debug("'" + offers.size() + "' offers fetched.");

    List<OfferEto> offerBos = new ArrayList<>(offers.size());
    for (OfferEntity o : offers) {
      offerBos.add(getBeanMapper().map(o, OfferEto.class));
    }
    return offerBos;
  }

}
