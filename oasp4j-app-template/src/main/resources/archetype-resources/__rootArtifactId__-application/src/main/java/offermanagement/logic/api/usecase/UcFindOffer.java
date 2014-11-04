#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.logic.api.usecase;

import ${package}.offermanagement.logic.api.to.OfferCto;
import ${package}.offermanagement.logic.api.to.OfferEto;
import ${package}.offermanagement.logic.api.to.OfferFilter;
import ${package}.offermanagement.logic.api.to.OfferSortBy;
import ${package}.offermanagement.logic.api.to.ProductEto;

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
   * @param id is the {@link ${package}.offermanagement.common.api.Offer${symbol_pound}getId() offer ID}.
   * @return the requested {@link ${package}.offermanagement.common.api.Offer} or <code>null</code>
   *         if no such {@link ${package}.offermanagement.common.api.Offer} exists.
   */
  OfferEto findOffer(Long id);

  /**
   * Gets an {@link OfferCto} using its entity identifier.
   *
   * @param id is the {@link ${package}.offermanagement.common.api.Offer${symbol_pound}getId() offer ID}.
   * @return the requested {@link ${package}.offermanagement.common.api.Offer} or <code>null</code>
   *         if no such {@link ${package}.offermanagement.common.api.Offer} exists.
   */
  OfferCto findOfferCto(Long id);

  /**
   * @return the {@link List} with all available {@link OfferEto}s.
   */
  List<OfferEto> findAllOffers();

  /**
   * Checks, whether a given {@link ProductEto} is in use by at least one {@link OfferEto}.
   *
   * @param product product to check if it is in use
   * @return <code>true</code>, if there are no {@link OfferEto offers}, that use the given {@link ProductEto}.
   *         <code>false</code> otherwise.
   */
  boolean isProductInUseByOffer(ProductEto product);

  /**
   * @param offerFilterBo is the {@link OfferFilter offers filter criteria}
   * @param sortBy is the {@link OfferSortBy} attribute, which defines the sorting.
   *
   * @return the {@link List} with all {@link OfferEto}s that match the {@link OfferFilter} criteria.
   */
  List<OfferEto> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy);

}
