#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.persistence.api.dao;

import ${package}.general.persistence.api.dao.ApplicationDao;
import ${package}.salesmanagement.logic.api.to.OrderSearchCriteriaTo;
import ${package}.salesmanagement.persistence.api.OrderEntity;

import java.util.List;

/**
 * {@link ApplicationDao Data Access Object} for {@link OrderEntity} entity.
 *
 * @author hohwille
 * @author rjoeris
 */
public interface OrderDao extends ApplicationDao<OrderEntity> {

  /**
   * @param tableId the {@link ${package}.tablemanagement.persistence.api.TableEntity${symbol_pound}getNumber()
   *        table ID} for which the open {@link OrderEntity} is requested.
   * @return the {@link OrderEntity} with the given {@link OrderEntity${symbol_pound}getTableId() table ID} in
   *         {@link OrderEntity${symbol_pound}getState() state}
   *         {@link ${package}.salesmanagement.common.api.datatype.OrderState${symbol_pound}OPEN} or
   *         <code>null</code> if no such entity exists.
   */
  OrderEntity findOpenOrderByTable(long tableId);

  /**
   * Finds the {@link OrderEntity orders} matching the given {@link OrderSearchCriteriaTo}.
   *
   * @param criteria is the {@link OrderSearchCriteriaTo}.
   * @return the matching {@link OrderEntity} or <code>null</code> if no such {@link OrderEntity} exists.
   */
  List<OrderEntity> findOrders(OrderSearchCriteriaTo criteria);

}
