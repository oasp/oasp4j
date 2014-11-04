#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.persistence.api.dao;

import ${package}.general.persistence.api.dao.ApplicationDao;
import ${package}.salesmanagement.persistence.api.OrderPositionEntity;

import java.util.List;

/**
 * {@link ApplicationDao Data Access Object} for {@link OrderPositionEntity} entity.
 *
 * @author hohwille
 */
public interface OrderPositionDao extends ApplicationDao<OrderPositionEntity> {

  /**
   * @param orderId is the {@link ${package}.salesmanagement.logic.api.to.OrderEto${symbol_pound}getId() ID} for
   *        which the {@link OrderPositionEntity}s are requested.
   * @return the {@link List} of all {@link OrderPositionEntity}s {@link OrderPositionEntity${symbol_pound}getOrderId() associated}
   *         with the given <code>orderId</code>.
   */
  List<OrderPositionEntity> findOrderPositionsByOrder(Long orderId);

  /**
   * @param orderId is the {@link ${package}.salesmanagement.logic.api.to.OrderEto${symbol_pound}getId() table ID}
   *        for which the {@link OrderPositionEntity}s are requested.
   * @return the {@link List} of all {@link OrderPositionEntity}s that are NOT
   *         {@link ${package}.salesmanagement.common.api.datatype.OrderPositionState${symbol_pound}isClosed()
   *         closed} and are {@link ${package}.salesmanagement.logic.api.to.OrderEto${symbol_pound}getId() table
   *         ID} with the given {@link ${package}.salesmanagement.logic.api.to.OrderEto}. Will be the
   *         empty {@link List} if no such {@link OrderPositionEntity} exists.
   */
  List<OrderPositionEntity> findOpenOrderPositionsByOrder(Long orderId);
}
