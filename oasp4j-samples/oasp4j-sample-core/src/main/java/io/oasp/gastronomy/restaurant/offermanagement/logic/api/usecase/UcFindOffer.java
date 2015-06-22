package io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferCto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductEto;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

/**
 * Interface for the usecsae to find {@link OfferEto offers}.
 *
 * @author mvielsac
 */
public interface UcFindOffer {

  /**
   * Gets an {@link OfferEto} using its entity identifier.
   *
   * @param id is the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getId() offer ID}.
   * @return the requested {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} or {@code null}
   *         if no such {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} exists.
   */
  OfferEto findOffer(Long id);

  /**
   * Gets an {@link OfferCto} using its entity identifier.
   *
   * @param id is the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getId() offer ID}.
   * @return the requested {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} or {@code null}
   *         if no such {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} exists.
   */
  OfferCto findOfferCto(Long id);

  /**
   * @return the {@link List} with all available {@link OfferEto}s.
   */
  @Deprecated
  List<OfferEto> findAllOffers();

  /**
   * Checks, whether a given {@link ProductEto} is in use by at least one {@link OfferEto}.
   *
   * @param product product to check if it is in use
   * @return {@code true}, if there are no {@link OfferEto offers}, that use the given {@link ProductEto}.
   *         {@code false} otherwise.
   */
  boolean isProductInUseByOffer(ProductEto product);

  /**
   * @param offerFilterBo is the {@link OfferFilter offers filter criteria}
   * @param sortBy is the {@link OfferSortBy} attribute, which defines the sorting.
   *
   * @return the {@link List} with all {@link OfferEto}s that match the {@link OfferFilter} criteria.
   */
  @Deprecated
  List<OfferEto> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy);

  /**
   * Returns a list of offers matching the search criteria.
   *
   * @param criteria the {@link OfferSearchCriteriaTo}.
   * @return the {@link List} of matching {@link OfferEto}s.
   */
  PaginatedListTo<OfferEto> findOfferEtos(OfferSearchCriteriaTo criteria);

}
