package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferSortByHitEntry;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.MealEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SideDishEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.OfferDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSortBy;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

/**
 * Implementation of {@link OfferDao}.
 *
 * @author loverbec
 */
@Named
public class OfferDaoImpl extends ApplicationMasterDataDaoImpl<OfferEntity> implements OfferDao {

  /**
   * The constructor.
   */
  public OfferDaoImpl() {

    super();
  }

  @Override
  public Class<OfferEntity> getEntityClass() {

    return OfferEntity.class;
  }

  @Override
  @Deprecated
  public List<OfferEntity> findOffersFiltered(OfferFilter offerFilterBo, OfferSortBy sortBy) {

    /*
     * Default error handling
     */
    if (offerFilterBo == null || sortBy == null) {
      return new ArrayList<>(0);
    }

    CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<OfferEntity> criteriaQuery = criteriaBuilder.createQuery(OfferEntity.class);

    Root<OfferEntity> offerRoot = criteriaQuery.from(OfferEntity.class);
    List<Predicate> predicateParameters = new ArrayList<>();

    /*
     * Applying the filters
     */
    // Meal
    Join<OfferEntity, MealEntity> mealJoin = null;
    if (offerFilterBo.getMealId() != null && offerFilterBo.getMealId() > 0) {
      mealJoin = offerRoot.join("meal");
      Path<Long> mealId = mealJoin.get("id");

      predicateParameters.add(criteriaBuilder.equal(mealId, offerFilterBo.getMealId()));
    }

    // Drink
    Join<OfferEntity, DrinkEntity> drinkJoin = null;
    if (offerFilterBo.getDrinkId() != null && offerFilterBo.getDrinkId() > 0) {
      drinkJoin = offerRoot.join("drink");
      Path<Long> drinkId = drinkJoin.get("id");

      predicateParameters.add(criteriaBuilder.equal(drinkId, offerFilterBo.getDrinkId()));
    }

    // SideDish
    Join<OfferEntity, SideDishEntity> sideDishJoin = null;
    if (offerFilterBo.getSideDishId() != null && offerFilterBo.getSideDishId() > 0) {
      sideDishJoin = offerRoot.join("sideDish");
      Path<Long> sideDishId = sideDishJoin.get("id");

      predicateParameters.add(criteriaBuilder.equal(sideDishId, offerFilterBo.getSideDishId()));
    }

    // REVIEW <who> (hohwille) the following code will IMHO not work at all as Query. If it does then it is still
    // incorrect as the conversion to double is imprecise.

    /*
     * Price
     */
    // only min price is given
    if (offerFilterBo.getMinPrice() != null) {
      Path<Money> offerCurrentPrice = offerRoot.get("currentPrice");
      predicateParameters.add(criteriaBuilder.ge(offerCurrentPrice.as(Double.class), offerFilterBo.getMinPrice()
          .getValue().doubleValue()));
    }

    // only max price is given
    if (offerFilterBo.getMaxPrice() != null) {
      Path<Money> offerCurrentPrice = offerRoot.get("currentPrice");
      predicateParameters.add(criteriaBuilder.le(offerCurrentPrice.as(Double.class), offerFilterBo.getMaxPrice()
          .getValue().doubleValue()));
    }

    /*
     * Build query
     */

    /*
     * Get the attribute name to sort by
     */
    // Default: Sort by attribute "id"
    Expression<?> sortByExpression = offerRoot.get("id");

    if (sortBy.getSortByEntry().equals(OfferSortByHitEntry.DESCRIPTION)) {
      sortByExpression = offerRoot.get("description");
    }
    if (sortBy.getSortByEntry().equals(OfferSortByHitEntry.PRICE)) {
      sortByExpression = offerRoot.get("currentPrice");
    }
    if (mealJoin != null && sortBy.getSortByEntry().equals(OfferSortByHitEntry.MEAL)) {
      sortByExpression = mealJoin.get("description");
    }
    if (drinkJoin != null && sortBy.getSortByEntry().equals(OfferSortByHitEntry.DRINK)) {
      sortByExpression = drinkJoin.get("description");
    }
    if (sideDishJoin != null && sortBy.getSortByEntry().equals(OfferSortByHitEntry.SIDEDISH)) {
      sortByExpression = sideDishJoin.get("description");
    }

    /*
     * Sorting order direction
     */
    // Default: Ascend sorting
    criteriaQuery.select(offerRoot).where(predicateParameters.toArray(new Predicate[] {}))
        .orderBy(criteriaBuilder.asc(sortByExpression));

    if (sortBy.getOrderBy().isDesc()) {
      /*
       * Descend ordering
       */

      criteriaQuery.select(offerRoot).where(predicateParameters.toArray(new Predicate[] {}))
          .orderBy(criteriaBuilder.desc(sortByExpression));
    }

    /*
     * Result
     */
    TypedQuery<OfferEntity> typedQuery = getEntityManager().createQuery(criteriaQuery);
    List<OfferEntity> result = typedQuery.getResultList();

    return result;
  }

  @Override
  public PaginatedListTo<OfferEntity> findOffers(OfferSearchCriteriaTo criteria) {

    OfferEntity offer = Alias.alias(OfferEntity.class);
    EntityPathBase<OfferEntity> alias = Alias.$(offer);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);

    Long number = criteria.getNumber();
    if (number != null) {
      query.where(Alias.$(offer.getNumber()).eq(number));
    }
    Long mealId = criteria.getMealId();
    if (mealId != null) {
      query.where(Alias.$(offer.getMealId()).eq(mealId));
    }
    Long drinkId = criteria.getDrinkId();
    if (drinkId != null) {
      query.where(Alias.$(offer.getDrinkId()).eq(drinkId));
    }
    Long sideDishId = criteria.getSideDishId();
    if (sideDishId != null) {
      query.where(Alias.$(offer.getSideDishId()).eq(sideDishId));
    }
    OfferState state = criteria.getState();
    if (state != null) {
      query.where(Alias.$(offer.getState()).eq(state));
    }

    Money minPrice = criteria.getMinPrice();
    if (minPrice != null) {
      query.where(Alias.$(offer.getPrice()).goe(minPrice));
    }

    Money maxPrice = criteria.getMaxPrice();
    if (maxPrice != null) {
      query.where(Alias.$(offer.getPrice()).loe(maxPrice));
    }

    return findPaginated(criteria, query, alias);
  }
}
