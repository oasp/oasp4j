package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

import java.util.List;

/**
 * {@link ApplicationDao Data Access Object} for {@link OfferEntity} entity.
 *
 */
public interface OfferDao extends ApplicationDao<OfferEntity>, MasterDataDao<OfferEntity> {

  /**
   * Returns a {@link List} of filtered {@link OfferEntity}s.
   *
   * @param offerFilterBo is the {@link OfferFilter offers filter criteria}. Any value, that should be applied as a
   *        filter, have to be different to {@code null}.
   * @param sortBy is the {@link OfferSortBy} attribute, which defines the sorting.
   *
   * @return the {@link List} with all {@link OfferEntity}s that match the {@link OfferFilter offers filter criteria}.
   */
  @Deprecated
  List<OfferEntity> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy);

  /**
   * Finds the {@link OfferEntity} objects matching the given {@link OfferSearchCriteriaTo}.
   *
   * @param criteria is the {@link OfferSearchCriteriaTo}.
   * @return the {@link List} with the matching {@link OfferEntity} objects.
   */
  PaginatedListTo<OfferEntity> findOffers(OfferSearchCriteriaTo criteria);

}
