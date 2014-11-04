#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.api.dao;

import ${package}.general.persistence.api.dao.ApplicationDao;
import ${package}.offermanagement.logic.api.to.OfferFilter;
import ${package}.offermanagement.logic.api.to.OfferSortBy;
import ${package}.offermanagement.persistence.api.OfferEntity;
import io.oasp.module.jpa.persistence.api.MasterDataDao;

import java.util.List;

/**
 * {@link ApplicationDao Data Access Object} for {@link OfferEntity} entity.
 *
 * @author loverbec
 */
public interface OfferDao extends ApplicationDao<OfferEntity>, MasterDataDao<OfferEntity> {

  /**
   * Returns a {@link List} of filtered {@link OfferEntity}s.
   *
   * @param offerFilterBo is the {@link OfferFilter offers filter criteria}. Any value, that should be applied as a
   *        filter, have to be different to <code>null</code>.
   * @param sortBy is the {@link OfferSortBy} attribute, which defines the sorting.
   *
   * @return the {@link List} with all {@link OfferEntity}s that match the {@link OfferFilter offers filter criteria}.
   */
  List<OfferEntity> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy);

}
