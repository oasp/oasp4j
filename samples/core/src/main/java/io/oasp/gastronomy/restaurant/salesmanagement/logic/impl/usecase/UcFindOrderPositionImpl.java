package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.logic.api.UseCase;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.usecase.UcFindOrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.base.usecase.AbstractOrderPositionUc;

/**
 * Implementation of {@link UcFindOrderPosition}.
 *
 * @author jozitz
 */
@Named
@UseCase
public class UcFindOrderPositionImpl extends AbstractOrderPositionUc implements UcFindOrderPosition {

  private static final Logger LOG = LoggerFactory.getLogger(UcFindOrderPositionImpl.class);

  @Override
  @RolesAllowed(PermissionConstants.FIND_ORDER_POSITION)
  public OrderPositionEto findOrderPosition(long orderPositionId) {

    LOG.debug("Get order position.");
    OrderPositionEntity orderPositionEntities = getOrderPositionDao().findOne(orderPositionId);
    OrderPositionEto orderPositionBo = getBeanMapper().map(orderPositionEntities, OrderPositionEto.class);
    return orderPositionBo;
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_ORDER_POSITION)
  // Modified RobertoGm
  public List<OrderPositionEto> findOrderPositionsByOrderId(long orderId, OrderPositionSearchCriteriaTo criteria) {

    // Modified RobertoGm
    List<OrderPositionEntity> positions = getOrderPositionDao().findOrderPositionsByOrder(orderId, criteria);
    return getBeanMapper().mapList(positions, OrderPositionEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_ORDER_POSITION)
  public List<OrderPositionEto> findOpenOrderPositionsByOrderId(long orderId) {

    LOG.debug("Get all open order positions for order id '" + orderId + "'.");
    return getBeanMapper().mapList(getOrderPositionDao().findOpenOrderPositionsByOrder(orderId),
        OrderPositionEto.class);
  }

  @Override
  @RolesAllowed(PermissionConstants.FIND_ORDER_POSITION)
  public List<OrderPositionEto> findOrderPositions(OrderPositionSearchCriteriaTo criteria) {

    List<OrderPositionEntity> positions = getOrderPositionDao().findOrderPositions(criteria);
    return getBeanMapper().mapList(positions, OrderPositionEto.class);
  }

}
