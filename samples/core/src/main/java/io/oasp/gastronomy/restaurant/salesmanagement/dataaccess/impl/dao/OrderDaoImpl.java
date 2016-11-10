package io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.impl.dao;

import static com.mysema.query.alias.Alias.$;
import io.oasp.gastronomy.restaurant.general.common.api.constants.NamedQueries;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationDaoImpl;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderDao;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

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

  @Override
  public Class<OrderEntity> getEntityClass() {

    return OrderEntity.class;
  }

  @Override
  public OrderEntity findOpenOrderByTable(long tableId) {

    List<OrderEntity> resultList =
        getEntityManager().createNamedQuery(NamedQueries.GET_OPEN_ORDER_FOR_TABLE, OrderEntity.class)
            .setParameter("tableId", tableId).getResultList();
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

  @Override
  public PaginatedListTo<OrderEntity> findOrders(OrderSearchCriteriaTo criteria) {

    OrderEntity order = Alias.alias(OrderEntity.class);
    EntityPathBase<OrderEntity> alias = $(order);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);

    Long tableId = criteria.getTableId();
    if (tableId != null) {
      query.where($(order.getTableId()).eq(tableId));
    }
    OrderState state = criteria.getState();
    if (state != null) {
      query.where($(order.getState()).eq(state));
    }

    return findPaginated(criteria, query, alias);
  }
}
