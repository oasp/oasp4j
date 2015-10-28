<<<<<<< HEAD
package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.common.AbstractSpringIntegrationTest;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderPositionDao;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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
   * Tests if the {@link OrderPositionState} is persisted correctly. The test modifies the {@link OrderPositionState} as
   * well as the drinkState {@link ProductOrderState}. The test focuses on saving {@link OrderPositionEto} saving and
   * verification of state change.
   */
  @Test
  public void testOrderPositionStateChange() {

    try {
      OrderEto order = new OrderEto();
      order.setTableId(1L);
      order = this.salesManagement.saveOrder(order);
      OrderPositionEto orderPosition = new OrderPositionEto();
      orderPosition.setOfferId(5L);
      orderPosition.setOrderId(order.getId());
      orderPosition.setOfferName("Cola");
      orderPosition.setPrice(new Money(1.2));

      orderPosition = this.salesManagement.saveOrderPosition(orderPosition);

      orderPosition.setState(OrderPositionState.ORDERED);
      orderPosition.setDrinkState(ProductOrderState.ORDERED);

      OrderPositionEto updatedOrderPosition = this.salesManagement.saveOrderPosition(orderPosition);
      assertThat(updatedOrderPosition.getState()).isEqualTo(OrderPositionState.ORDERED);

      updatedOrderPosition.setState(OrderPositionState.PREPARED);
      updatedOrderPosition.setDrinkState(ProductOrderState.PREPARED);

      updatedOrderPosition = this.salesManagement.saveOrderPosition(updatedOrderPosition);

      assertThat(updatedOrderPosition.getState()).isEqualTo(OrderPositionState.PREPARED);
    } catch (ConstraintViolationException e) {
      // BV is really painful as you need such code to see the actual error in JUnit.
      StringBuilder sb = new StringBuilder(64);
      sb.append("Constraints violated:");
      for (ConstraintViolation<?> v : e.getConstraintViolations()) {
        sb.append("\n");
        sb.append(v.getPropertyPath());
        sb.append(":");
        sb.append(v.getMessage());
      }
      throw new IllegalStateException(sb.toString(), e);
    }

  }

}
=======
package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.common.AbstractSpringIntegrationTest;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.dataaccess.api.dao.OrderPositionDao;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.module.configuration.common.api.ApplicationConfigurationConstants;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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
   * Tests if the {@link OrderPositionState} is persisted correctly. The test modifies the {@link OrderPositionState} as
   * well as the drinkState {@link ProductOrderState}. The test focuses on saving {@link OrderPositionEto} saving and
   * verification of state change.
   */
  @Test
  public void testOrderPositionStateChange() {

    try {
      OrderEto order = new OrderEto();
      order.setTableId(1L);
      order = this.salesManagement.saveOrder(order);
      OrderPositionEto orderPosition = new OrderPositionEto();
      orderPosition.setOfferId(5L);
      orderPosition.setOrderId(order.getId());
      orderPosition.setOfferName("Cola");
      orderPosition.setPrice(new Money(1.2));

      orderPosition = this.salesManagement.saveOrderPosition(orderPosition);

      orderPosition.setState(OrderPositionState.ORDERED);
      orderPosition.setDrinkState(ProductOrderState.ORDERED);

      OrderPositionEto updatedOrderPosition = this.salesManagement.saveOrderPosition(orderPosition);
      assertThat(updatedOrderPosition.getState()).isEqualTo(OrderPositionState.ORDERED);

      updatedOrderPosition.setState(OrderPositionState.PREPARED);
      updatedOrderPosition.setDrinkState(ProductOrderState.PREPARED);

      updatedOrderPosition = this.salesManagement.saveOrderPosition(updatedOrderPosition);

      assertThat(updatedOrderPosition.getState()).isEqualTo(OrderPositionState.PREPARED);
    } catch (ConstraintViolationException e) {
      // BV is really painful as you need such code to see the actual error in JUnit.
      StringBuilder sb = new StringBuilder(64);
      sb.append("Constraints violated:");
      for (ConstraintViolation<?> v : e.getConstraintViolations()) {
        sb.append("\n");
        sb.append(v.getPropertyPath());
        sb.append(":");
        sb.append(v.getMessage());
      }
      throw new IllegalStateException(sb.toString(), e);
    }

  }

}
>>>>>>> 44c17c8... #199:Cleaned up exisiting test-cases as described
