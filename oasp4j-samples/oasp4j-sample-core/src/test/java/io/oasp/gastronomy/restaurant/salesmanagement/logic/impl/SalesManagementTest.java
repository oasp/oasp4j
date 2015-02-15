package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.common.AbstractSpringIntegrationTest;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.OrderPositionEntity;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderPositionDao;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is the test-case of {@link Salesmanagement}.
 *
 * @author hohwille
 */
@ContextConfiguration({ ApplicationConfigurationConstants.BEANS_LOGIC })
public class SalesManagementTest extends AbstractSpringIntegrationTest {

  @Inject
  private Salesmanagement salesManagement;

  @Inject
  private OrderPositionDao orderPositionDao;

  /**
   * Tests if a Bill is persisted correctly. Special focus is on the mapping of {@link Money} and furthermore the query
   * of one of the {@link Money} fields is tested.
   */
  @Test
  public void testSalesmanagement() {

    TableEto table = new TableEto();
    Long tableId = 1L;
    table.setId(tableId);
    OrderEto order = this.salesManagement.saveOrder(table);
    assertEquals(tableId, Long.valueOf(order.getTableId()));
  }

  /**
   * Tests if the {@link OrderPositionState} is persisted correctly. The test modifies the {@link OrderPositionState} as
   * well as the drinkState {@link ProductOrderState}. The test focuses on saving {@link OrderPositionEto} saving and
   * verification of state change.
   *
   */

  @Test
  public void testOrderPositionStateChange() {

    OrderPositionEntity orderPosition = new OrderPositionEntity();
    this.orderPositionDao.save(orderPosition);
    assertNotNull(orderPosition.getId());

    OrderPositionEto orderPositionEto = new OrderPositionEto();

    orderPositionEto.setId(orderPosition.getId());
    orderPositionEto.setState(OrderPositionState.ORDERED);
    orderPositionEto.setDrinkState(ProductOrderState.ORDERED);

    OrderPositionEto loadedOrderPositionEto = this.salesManagement.saveOrderPosition(orderPositionEto);

    assertEquals(loadedOrderPositionEto.getState(), OrderPositionState.ORDERED);

    loadedOrderPositionEto.setState(OrderPositionState.PREPARED);
    loadedOrderPositionEto.setDrinkState(ProductOrderState.PREPARED);

    OrderPositionEto updatedOrderPositionEto = this.salesManagement.saveOrderPosition(loadedOrderPositionEto);

    assertEquals(updatedOrderPositionEto.getState(), OrderPositionState.PREPARED);

  }

}
