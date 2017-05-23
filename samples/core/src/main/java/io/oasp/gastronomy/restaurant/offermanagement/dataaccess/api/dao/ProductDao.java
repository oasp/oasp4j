package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationRevisionedDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.AbstractProductEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

import java.util.List;

/**
 * {@link ApplicationRevisionedDao Data Access Object} for {@link AbstractProductEntity} entity.
 *
 */
public interface ProductDao extends ApplicationRevisionedDao<AbstractProductEntity>, MasterDataDao<AbstractProductEntity> {

  /**
   * @param productFilter is the {@link ProductFilter}.
   * @param sortBy is the {@link ProductSortBy} criteria.
   * @return the {@link List} of filtered and sorted {@link AbstractProductEntity products}.
   */
  @Deprecated
  List<AbstractProductEntity> findProductsFiltered(ProductFilter productFilter, ProductSortBy sortBy);

  /**
   * Finds the {@link AbstractProductEntity} objects matching the given {@link ProductSearchCriteriaTo}.
   *
   * @param criteria is the {@link ProductSearchCriteriaTo}.
   * @return the {@link List} with the matching {@link AbstractProductEntity} objects.
   */
  PaginatedListTo<AbstractProductEntity> findProducts(ProductSearchCriteriaTo criteria);

}
