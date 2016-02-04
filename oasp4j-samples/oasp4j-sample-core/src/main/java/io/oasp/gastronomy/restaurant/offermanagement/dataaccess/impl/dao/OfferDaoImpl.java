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

import com.mysema.query.BooleanBuilder;
import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.JPQLQuery;
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

    if (offerFilterBo == null || sortBy == null) {
      return new ArrayList<>(0);
    }

    OfferEntity offer = Alias.alias(OfferEntity.class);
    JPQLQuery query = new JPAQuery(getEntityManager()).from(Alias.$(offer));
    BooleanBuilder builder = new BooleanBuilder();

    MealEntity meal = Alias.alias(MealEntity.class);
    if (offerFilterBo.getMealId() != null && offerFilterBo.getMealId() > 0) {
      query = query.join(Alias.$(offer.getMeal()), Alias.$(meal));
      builder.and(Alias.$(meal.getId()).eq(offerFilterBo.getMealId()));
    }

    DrinkEntity drink = Alias.alias(DrinkEntity.class);
    if (offerFilterBo.getDrinkId() != null && offerFilterBo.getDrinkId() > 0) {
      query.join(Alias.$(offer.getDrink()), Alias.$(drink));
      builder.and(Alias.$(drink.getId()).eq(offerFilterBo.getDrinkId()));
    }

    SideDishEntity sideDish = Alias.alias(SideDishEntity.class);
    if (offerFilterBo.getSideDishId() != null && offerFilterBo.getSideDishId() > 0) {
      query.join(Alias.$(offer.getSideDish()), Alias.$(sideDish));
      builder.and(Alias.$(sideDish.getId()).eq(offerFilterBo.getSideDishId()));
    }

    // only min price is given
    if (offerFilterBo.getMinPrice() != null) {
      builder.and(Alias.$(offer.getPrice()).goe(offerFilterBo.getMinPrice()));
    }

    // only max price is given
    if (offerFilterBo.getMaxPrice() != null) {
      builder.and(Alias.$(offer.getPrice()).loe(offerFilterBo.getMaxPrice()));
    }

    if (sortBy.getSortByEntry().equals(OfferSortByHitEntry.DESCRIPTION)) {
      if (sortBy.getOrderBy().isDesc())
        query.where(builder).orderBy(Alias.$(offer.getDescription()).desc());
      else
        query.where(builder).orderBy(Alias.$(offer.getDescription()).asc());
    } else if (sortBy.getSortByEntry().equals(OfferSortByHitEntry.PRICE)) {
      if (sortBy.getOrderBy().isDesc())
        query.where(builder).orderBy(Alias.$(offer.getPrice()).desc());
      else
        query.where(builder).orderBy(Alias.$(offer.getPrice()).asc());
    } else if (sortBy.getSortByEntry().equals(OfferSortByHitEntry.MEAL)) {
      if (sortBy.getOrderBy().isDesc())
        query.where(builder).orderBy(Alias.$(offer.getMeal().getDescription()).desc());
      else
        query.where(builder).orderBy(Alias.$(offer.getMeal().getDescription()).asc());
    } else if (sortBy.getSortByEntry().equals(OfferSortByHitEntry.DRINK)) {
      if (sortBy.getOrderBy().isDesc())
        query.where(builder).orderBy(Alias.$(offer.getDrink().getDescription()).desc());
      else
        query.where(builder).orderBy(Alias.$(offer.getDrink().getDescription()).asc());
    } else if (sortBy.getSortByEntry().equals(OfferSortByHitEntry.SIDEDISH)) {
      if (sortBy.getOrderBy().isDesc())
        query.where(builder).orderBy(Alias.$(offer.getSideDish().getDescription()).desc());
      else
        query.where(builder).orderBy(Alias.$(offer.getSideDish().getDescription()).asc());
    } else {
      if (sortBy.getOrderBy().isDesc())
        query.where(builder).orderBy(Alias.$(offer.getId()).desc());
      else
        query.where(builder).orderBy(Alias.$(offer.getId()).asc());
    }

    List<OfferEntity> result = query.where(builder).list(Alias.$(offer));
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
