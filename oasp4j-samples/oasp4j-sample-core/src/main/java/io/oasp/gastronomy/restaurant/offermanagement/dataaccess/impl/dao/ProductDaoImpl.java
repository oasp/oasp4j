package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.ProductSortByHitEntry;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.MealEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.ProductEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SideDishEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.ProductDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

/**
 * Implementation of {@link ProductDao}.
 *
 * @author loverbec
 */
@Named
public class ProductDaoImpl extends ApplicationMasterDataDaoImpl<ProductEntity> implements ProductDao {

  /**
   * The constructor.
   */
  public ProductDaoImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<ProductEntity> getEntityClass() {

    return ProductEntity.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ProductEntity> findProductsFiltered(ProductFilter productFilterBo, ProductSortBy sortBy) {

    /*
     * Basic error handling
     */
    if (productFilterBo == null) {
      return new ArrayList<>();
    }

    CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<ProductEntity> criteriaQuery = criteriaBuilder.createQuery(ProductEntity.class);

    Root<ProductEntity> productRoot = criteriaQuery.from(ProductEntity.class);
    List<Subquery<? extends ProductEntity>> subQueries = new ArrayList<>();
    // for sorting
    List<Predicate> predicateParameters = new ArrayList<>();

    /*
     * Drinks
     */
    if (productFilterBo.getFetchDrinks()) {
      Subquery<DrinkEntity> drinkSubQuery = criteriaQuery.subquery(DrinkEntity.class);
      Root<DrinkEntity> drinkRoot = drinkSubQuery.from(DrinkEntity.class);
      drinkSubQuery.select(drinkRoot);

      subQueries.add(drinkSubQuery);
    }

    /*
     * Meals
     */
    if (productFilterBo.getFetchMeals()) {
      Subquery<MealEntity> mealSubQuery = criteriaQuery.subquery(MealEntity.class);
      Root<MealEntity> mealRoot = mealSubQuery.from(MealEntity.class);
      mealSubQuery.select(mealRoot);

      subQueries.add(mealSubQuery);
    }

    /*
     * SideDishes
     */
    if (productFilterBo.getFetchSideDishes()) {
      Subquery<SideDishEntity> sideDishSubQuery = criteriaQuery.subquery(SideDishEntity.class);
      Root<SideDishEntity> sideDishRoot = sideDishSubQuery.from(SideDishEntity.class);
      sideDishSubQuery.select(sideDishRoot);

      subQueries.add(sideDishSubQuery);
    }

    /*
     * Build query
     */

    /*
     * Get the attribute name to sort by
     */
    // Default: Sort by attribute "id"
    Expression<?> sortByExpression = productRoot.get("id");

    if (sortBy.getSortByEntry().equals(ProductSortByHitEntry.DESCRIPTION)) {
      sortByExpression = productRoot.get("description");
    }

    // Doesn't work for the discriminator value. Discriminator values are only accessable through where clauses.
    // See Ticket #40 to get more information!
    // if (sortBy.getSortByEntry().equals(ProductSortByHitEntry.DTYPE)) {
    // sortByExpression = productRoot.get("class");
    // }

    /*
     * Sorting order direction
     */
    // Default: Ascend sorting
    criteriaQuery.select(productRoot).where(predicateParameters.toArray(new Predicate[] {}))
        .orderBy(criteriaBuilder.asc(sortByExpression));

    if (sortBy.getOrderBy().isDesc()) {
      // Descend ordering
      criteriaQuery.select(productRoot).where(predicateParameters.toArray(new Predicate[] {}))
          .orderBy(criteriaBuilder.desc(sortByExpression));
    }

    /*
     * Result
     */
    List<Predicate> subtypesPredicateParameter = new ArrayList<>();
    for (Subquery<? extends ProductEntity> subQuery : subQueries) {
      subtypesPredicateParameter.add(criteriaBuilder.in(productRoot).value(subQuery));
    }

    // criteriaQuery.select(productRoot);
    criteriaQuery.where(criteriaBuilder.or(subtypesPredicateParameter.toArray(new Predicate[] {})));
    // criteriaQuery.orderBy(criteriaBuilder.asc(productRoot.get("id")));

    List<ProductEntity> result = getEntityManager().createQuery(criteriaQuery).getResultList();

    return result;
  }
}
