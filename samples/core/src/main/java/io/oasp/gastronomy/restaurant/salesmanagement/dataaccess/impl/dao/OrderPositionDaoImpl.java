package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao;

import static com.querydsl.core.alias.Alias.$;

import java.util.List;

import javax.inject.Named;

import com.querydsl.core.alias.Alias;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import io.oasp.gastronomy.restaurant.general.common.api.constants.NamedQueries;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderPositionDao;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionSearchCriteriaTo;

/**
 * Implementation of {@link OrderPositionDao}.
 */
@Named
public class OrderPositionDaoImpl extends ApplicationDaoImpl<OrderPositionEntity> implements OrderPositionDao {

  @Override
  public Class<OrderPositionEntity> getEntityClass() {

    return OrderPositionEntity.class;
  }

  @Override
  public List<OrderPositionEntity> findOrderPositionsByOrder(Long orderId) {

    OrderPositionEntity orderPosition = Alias.alias(OrderPositionEntity.class);
    EntityPathBase<OrderPositionEntity> alias = $(orderPosition);
    JPAQuery<OrderPositionEntity> query = new JPAQuery<OrderPositionEntity>(getEntityManager()).from(alias);
    query.where($(orderPosition.getOrder().getId()).eq(orderId));
    return query.fetch();
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
    EntityPathBase<OrderPositionEntity> alias = $(orderPosition);
    JPAQuery<OrderPositionEntity> query = new JPAQuery<OrderPositionEntity>(getEntityManager()).from(alias);
    Long orderId = criteria.getOrderId();
    if (orderId != null) {
      query.where($(orderPosition.getOrder().getId()).eq(orderId));
    }
    Long cookId = criteria.getCookId();
    if (cookId != null) {
      query.where($(orderPosition.getCookId()).eq(cookId));
    }
    OrderPositionState state = criteria.getState();
    if (state != null) {
      query.where($(orderPosition.getState()).eq(state));
    }
    ProductOrderState drinkState = criteria.getDrinkState();
    if (drinkState != null) {
      query.where($(orderPosition.getDrinkState()).eq(drinkState));
    }
    if (criteria.isMealOrSideDish()) {
      OfferEntity offer = Alias.alias(OfferEntity.class);
      EntityPathBase<OfferEntity> offerAlias = $(offer);
      JPQLQuery<Long> subQuery = JPAExpressions.select($(offer.getId())).from(offerAlias)
          .where($(offer.getMeal().getId()).isNotNull().or($(offer.getSideDish().getId()).isNotNull()));
      query.where($(orderPosition.getOfferId()).in(subQuery));
    }
    applyPagination(criteria.getPagination(), query);
    return query.fetch();
  }
}
