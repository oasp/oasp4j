#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.salesmanagement.logic.impl.usecase;

import ${package}.salesmanagement.logic.api.to.OrderPositionEto;
import ${package}.salesmanagement.logic.api.usecase.UcFindOrderPosition;
import ${package}.salesmanagement.logic.base.usecase.AbstractOrderPositionUc;
import ${package}.salesmanagement.persistence.api.OrderPositionEntity;

import java.util.List;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link UcFindOrderPosition}.
 *
 * @author jozitz
 */
@Named
public class UcFindOrderPositionImpl extends AbstractOrderPositionUc implements UcFindOrderPosition {

  private static final Logger LOG = LoggerFactory.getLogger(UcFindOrderPositionImpl.class);

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderPositionEto findOrderPosition(Long orderPositionId) {

    LOG.debug("Get order position.");
    OrderPositionEntity orderPositionEntities = getOrderPositionDao().findOne(orderPositionId);
    OrderPositionEto orderPositionBo = getBeanMapper().map(orderPositionEntities, OrderPositionEto.class);
    return orderPositionBo;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderPositionEto> findOrderPositionsByOrderId(Long orderId) {

    List<OrderPositionEntity> positions = getOrderPositionDao().findOrderPositionsByOrder(orderId);
    return getBeanMapper().mapList(positions, OrderPositionEto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OrderPositionEto> findOpenOrderPositionsByOrderId(Long orderId) {

    LOG.debug("Get all open order positions for order id '" + orderId + "'.");
    return getBeanMapper()
        .mapList(getOrderPositionDao().findOpenOrderPositionsByOrder(orderId), OrderPositionEto.class);
  }

}
