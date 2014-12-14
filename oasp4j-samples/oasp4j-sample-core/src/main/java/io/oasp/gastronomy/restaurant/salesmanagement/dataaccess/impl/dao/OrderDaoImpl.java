package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.common.api.constants.NamedQueries;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationDaoImpl;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderDao;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;

import java.util.List;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

/**
 * Implementation of {@link OrderDao}.
 *
 * @author rjoeris
 */
@Named
public class OrderDaoImpl extends ApplicationDaoImpl<OrderEntity> implements OrderDao {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(OrderDaoImpl.class);

  /**
   * The constructor.
   */
  public OrderDaoImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<OrderEntity> getEntityClass() {

    return OrderEntity.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderEntity findOpenOrderByTable(long tableId) {

    List<OrderEntity> resultList =
        getEntityManager().createNamedQuery(NamedQueries.GET_OPEN_ORDER_FOR_TABLE, OrderEntity.class)
            .setParameter(1, tableId).getResultList();
    if (resultList.isEmpty()) {
      return null;
    } else {
      if (resultList.size() > 1) {
        // In this case we can be robust and continue even though a data inconsistency has been detected.
        LOG.error("Multiple open orders found for table {}: {}", Long.valueOf(tableId), resultList);
      }
      return resultList.get(0);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderEntity> findOrders(OrderSearchCriteriaTo criteria) {

    OrderEntity order = Alias.alias(OrderEntity.class);
    EntityPathBase<OrderEntity> alias = Alias.$(order);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);
    Long tableId = criteria.getTableId();
    if (tableId != null) {
      query.where(Alias.$(order.getTableId()).eq(tableId));
    }
    OrderState state = criteria.getState();
    if (state != null) {
      query.where(Alias.$(order.getState()).eq(state));
    }
    applyCriteria(criteria, query);
    return query.list(alias);
  }
}
