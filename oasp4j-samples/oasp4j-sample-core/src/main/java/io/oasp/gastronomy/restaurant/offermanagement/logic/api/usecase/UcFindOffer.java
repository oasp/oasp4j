package io.oasp.gastronomy.restaurant.offermanagement.logic.api.usecase;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferCto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.List;

/**
 * Interface of UcFindOffer to centralize documentation and signatures of methods.
 *
 * @author mbrunnli
 * @since dev
 */
public interface UcFindOffer {

  /**
   * Gets an {@link OfferEto} using its entity identifier.
   *
   * @param id is the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getId() offer ID}.
   * @return the requested {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} or {@code null} if no
   *         such {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} exists.
   */
  OfferEto findOffer(Long id);

  /**
   * Gets an {@link OfferCto} using its entity identifier.
   *
   * @param id is the {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer#getId() offer ID}.
   * @return the requested {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} or {@code null} if no
   *         such {@link io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer} exists.
   */
  OfferCto findOfferCto(Long id);

  /**
   * @return the {@link List} with all available {@link OfferEto}s.
   */
  List<OfferEto> findAllOffers();

  /**
   * Returns a list of offers matching the search criteria.
   *
   * @param criteria the {@link OfferSearchCriteriaTo}.
   * @return the {@link List} of matching {@link OfferEto}s.
   */
  PaginatedListTo<OfferEto> findOfferEtos(OfferSearchCriteriaTo criteria);

  /**
   * @param offerFilterBo is the {@link OfferFilter offers filter criteria}
   * @param sortBy is the {@link OfferSortBy} attribute, which defines the sorting.
   * @return the {@link List} with all {@link OfferEto}s that match the {@link OfferFilter} criteria.
   */
  List<OfferEto> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy);

}
