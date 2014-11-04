#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.persistence.impl.dao;

import ${package}.general.common.api.constants.NamedQueries;
import ${package}.general.persistence.base.dao.ApplicationDaoImpl;
import ${package}.salesmanagement.persistence.api.OrderPositionEntity;
import ${package}.salesmanagement.persistence.api.dao.OrderPositionDao;

import java.util.List;

import javax.inject.Named;

import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

/**
 * Implementation of {@link OrderPositionDao}.
 *
 * @author hohwille
 */
@Named
public class OrderPositionDaoImpl extends ApplicationDaoImpl<OrderPositionEntity> implements OrderPositionDao {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<OrderPositionEntity> getEntityClass() {

    return OrderPositionEntity.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderPositionEntity> findOrderPositionsByOrder(Long orderId) {

    OrderPositionEntity orderPosition = Alias.alias(OrderPositionEntity.class);
    EntityPathBase<OrderPositionEntity> alias = Alias.${symbol_dollar}(orderPosition);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);
    query.where(Alias.${symbol_dollar}(orderPosition.getOrder().getId()).eq(orderId));
    return query.list(alias);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderPositionEntity> findOpenOrderPositionsByOrder(Long orderId) {

    return getEntityManager()
        .createNamedQuery(NamedQueries.GET_OPEN_ORDER_POSITIONS_FOR_ORDER, OrderPositionEntity.class)
        .setParameter(1, orderId).getResultList();
  }

}
