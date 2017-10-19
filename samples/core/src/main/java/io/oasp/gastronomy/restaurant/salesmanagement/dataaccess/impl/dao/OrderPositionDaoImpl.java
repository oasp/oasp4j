package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao;

import static com.mysema.query.alias.Alias.$;

import java.util.List;

import javax.inject.Named;

import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.query.ListSubQuery;

import io.oasp.gastronomy.restaurant.general.common.api.constants.NamedQueries;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderPositionDao;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.OrderByTo;
import io.oasp.module.jpa.common.api.to.OrderDirection;

/**
 * Implementation of {@link OrderPositionDao}.
 *
 * @author hohwille
 */
@Named
public class OrderPositionDaoImpl extends ApplicationDaoImpl<OrderPositionEntity> implements OrderPositionDao {

  @Override
  public Class<OrderPositionEntity> getEntityClass() {

    return OrderPositionEntity.class;
  }

  @Override
  public List<OrderPositionEntity> findOrderPositionsByOrder(Long orderId, OrderPositionSearchCriteriaTo criteria) {

    OrderPositionEntity orderPosition = Alias.alias(OrderPositionEntity.class);
    EntityPathBase<OrderPositionEntity> alias = Alias.$(orderPosition);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);
    query.where(Alias.$(orderPosition.getOrder().getId()).eq(orderId));
    if (criteria != null) {
      addOrderBy(query, alias, orderPosition, criteria.getSort());
    }

    return query.list(alias);
  }

  @Override
  public List<OrderPositionEntity> findOpenOrderPositionsByOrder(Long orderId) {

    return getEntityManager()
        .createNamedQuery(NamedQueries.GET_OPEN_ORDER_POSITIONS_FOR_ORDER, OrderPositionEntity.class)
        .setParameter("orderId", orderId).getResultList();
  }

  @Override
  public List<OrderPositionEntity> findOrderPositions(OrderPositionSearchCriteriaTo criteria) {

    OrderPositionEntity orderPosition = Alias.alias(OrderPositionEntity.class);
    EntityPathBase<OrderPositionEntity> alias = Alias.$(orderPosition);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);
    Long orderId = criteria.getOrderId();
    if (orderId != null) {
      query.where(Alias.$(orderPosition.getOrder().getId()).eq(orderId));
    }
    Long cookId = criteria.getCookId();
    if (cookId != null) {
      query.where(Alias.$(orderPosition.getCookId()).eq(cookId));
    }
    OrderPositionState state = criteria.getState();
    if (state != null) {
      query.where(Alias.$(orderPosition.getState()).eq(state));
    }
    String offerName = criteria.getOfferName();
    if (offerName != null) {
      if (!offerName.isEmpty()) {
        query.where(Alias.$(orderPosition.getOfferName()).eq(offerName));
      }
    }
    ProductOrderState drinkState = criteria.getDrinkState();
    if (drinkState != null) {
      query.where(Alias.$(orderPosition.getDrinkState()).eq(drinkState));
    }
    if (criteria.isMealOrSideDish()) {
      OfferEntity offer = Alias.alias(OfferEntity.class);
      EntityPathBase<OfferEntity> offerAlias = Alias.$(offer);
      JPASubQuery subQuery = new JPASubQuery().from(offerAlias)
          .where(Alias.$(offer.getMeal().getId()).isNotNull().or(Alias.$(offer.getSideDish().getId()).isNotNull()));
      ListSubQuery<Long> listSubQuery = subQuery.list(Alias.$(offer.getId()));
      query.where(Alias.$(orderPosition.getOffer().getId()).in(listSubQuery));

      //
      //
      // query.innerJoin(offer.).on(Alias.$(orderPosition.getOfferId()).eq(Alias.$(offer.getId())))
      // .where(Alias.$(offer.getMealId()).isNotNull().or(Alias.$(offer.getSideDishId()).isNotNull()));
    }
    String mealName = criteria.getMealName();
    if (mealName != null) {
      if (!mealName.isEmpty()) {
        OfferEntity offer = Alias.alias(OfferEntity.class);
        EntityPathBase<OfferEntity> offerAlias = Alias.$(offer);
        JPASubQuery subQuery =
            new JPASubQuery().from(offerAlias).where(Alias.$(offer.getMeal().getDescription()).eq(mealName));
        ListSubQuery<Long> listSubQuery = subQuery.list(Alias.$(offer.getId()));
        query.where(Alias.$(orderPosition.getOffer().getId()).in(listSubQuery));
      }
    }
    String sideDishName = criteria.getSideDishName();
    if (sideDishName != null) {
      if (!sideDishName.isEmpty()) {
        OfferEntity offer = Alias.alias(OfferEntity.class);
        EntityPathBase<OfferEntity> offerAlias = Alias.$(offer);
        JPASubQuery subQuery =
            new JPASubQuery().from(offerAlias).where(Alias.$(offer.getSideDish().getDescription()).eq(sideDishName));
        ListSubQuery<Long> listSubQuery = subQuery.list(Alias.$(offer.getId()));
        query.where(Alias.$(orderPosition.getOffer().getId()).in(listSubQuery));
      }
    }

    // Add order by fields
    addOrderBy(query, alias, orderPosition, criteria.getSort());

    applyPagination(criteria.getPagination(), query);
    return query.list(alias);
  }

  private void addOrderBy(JPAQuery query, EntityPathBase<OrderPositionEntity> alias, OrderPositionEntity orderPosition,
      List<OrderByTo> sort) {

    if (sort != null && !sort.isEmpty()) {
      for (OrderByTo orderEntry : sort) {
        if ("id".equals(orderEntry.getName())) {

          if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
            query.orderBy($(orderPosition.getId()).asc());
          } else {
            query.orderBy($(orderPosition.getId()).desc());
          }

        } else if ("offerName".equals(orderEntry.getName())) {

          if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
            query.orderBy($(orderPosition.getOfferName()).asc());
          } else {
            query.orderBy($(orderPosition.getOfferName()).desc());
          }

        } else if ("mealName".equals(orderEntry.getName())) {

          if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
            query.orderBy($(orderPosition.getOffer().getMeal().getDescription()).asc());
          } else {
            query.orderBy($(orderPosition.getOffer().getMeal().getDescription()).desc());
          }

        } else if ("sideDishName".equals(orderEntry.getName())) {

          if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
            query.orderBy($(orderPosition.getOffer().getSideDish().getDescription()).asc());
          } else {
            query.orderBy($(orderPosition.getOffer().getSideDish().getDescription()).desc());
          }

        } else if ("orderId".equals(orderEntry.getName())) {

          if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
            query.orderBy($(orderPosition.getOrder().getId()).asc());
          } else {
            query.orderBy($(orderPosition.getOrder().getId()).desc());
          }

        } else if ("state".equals(orderEntry.getName())) {

          if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
            query.orderBy($(orderPosition.getState()).asc());
          } else {
            query.orderBy($(orderPosition.getState()).desc());
          }

        } else if ("price".equals(orderEntry.getName())) {

          if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
            query.orderBy($(orderPosition.getPrice()).asc());
          } else {
            query.orderBy($(orderPosition.getPrice()).desc());
          }

        } else if ("comment".equals(orderEntry.getName())) {

          if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
            query.orderBy($(orderPosition.getComment()).asc());
          } else {
            query.orderBy($(orderPosition.getComment()).desc());
          }
        }
      }
    }
  }
}
