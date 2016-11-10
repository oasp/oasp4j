package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.common.api.constants.NamedQueries;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderPositionDao;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionSearchCriteriaTo;

import java.util.List;

import javax.inject.Named;

import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.query.ListSubQuery;

/**
 * Implementation of {@link OrderPositionDao}.
 *
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
    EntityPathBase<OrderPositionEntity> alias = Alias.$(orderPosition);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);
    query.where(Alias.$(orderPosition.getOrder().getId()).eq(orderId));
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
    ProductOrderState drinkState = criteria.getDrinkState();
    if (drinkState != null) {
      query.where(Alias.$(orderPosition.getDrinkState()).eq(drinkState));
    }
    if (criteria.isMealOrSideDish()) {
      OfferEntity offer = Alias.alias(OfferEntity.class);
      EntityPathBase<OfferEntity> offerAlias = Alias.$(offer);
      JPASubQuery subQuery =
          new JPASubQuery().from(offerAlias).where(
              Alias.$(offer.getMeal().getId()).isNotNull().or(Alias.$(offer.getSideDish().getId()).isNotNull()));
      ListSubQuery<Long> listSubQuery = subQuery.list(Alias.$(offer.getId()));
      query.where(Alias.$(orderPosition.getOfferId()).in(listSubQuery));

      //
      //
      // query.innerJoin(offer.).on(Alias.$(orderPosition.getOfferId()).eq(Alias.$(offer.getId())))
      // .where(Alias.$(offer.getMealId()).isNotNull().or(Alias.$(offer.getSideDishId()).isNotNull()));
    }
    applyPagination(criteria.getPagination(), query);
    return query.list(alias);
  }
}
