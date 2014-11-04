#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.persistence.api.dao;

import ${package}.general.persistence.api.dao.ApplicationDao;
import ${package}.offermanagement.logic.api.to.ProductFilter;
import ${package}.offermanagement.logic.api.to.ProductSortBy;
import ${package}.offermanagement.persistence.api.ProductEntity;
import io.oasp.module.jpa.persistence.api.MasterDataDao;

import java.util.List;

/**
 * {@link ApplicationDao Data Access Object} for {@link ProductEntity} entity.
 *
 * @author loverbec
 */
public interface ProductDao extends ApplicationDao<ProductEntity>, MasterDataDao<ProductEntity> {

  /**
   * @param productFilter is the {@link ProductFilter}.
   * @param sortBy is the {@link ProductSortBy} criteria.
   * @return the {@link List} of filtered and sorted {@link ProductEntity products}.
   */
  List<ProductEntity> findProductsFiltered(ProductFilter productFilter, ProductSortBy sortBy);

}
