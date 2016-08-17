package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.common.test.SampleCreator;
import io.oasp.gastronomy.restaurant.general.common.TestUtil;
import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.Salesmanagement;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.module.test.common.base.ComponentTest;
import io.oasp.module.test.common.helper.api.DbTestHelper;

/**
 * This is the test-case of {@link Salesmanagement}.
 *
 * @author hohwille, sroeger, shuber
 */
@SpringApplicationConfiguration(classes = { SpringBootApp.class })
@WebAppConfiguration
public class SalesManagementTest extends ComponentTest {

  @Inject
  private Salesmanagement salesManagement;

  /**
   * Log in utility for the test.
   */
  @Override
  public void doSetUp() {

    super.doSetUp();
    TestUtil.login("waiter", PermissionConstants.FIND_ORDER_POSITION, PermissionConstants.SAVE_ORDER_POSITION,
        PermissionConstants.SAVE_ORDER, PermissionConstants.FIND_OFFER);
  }

  /**
   * Log out utility for the test.
   */
  @Override
  public void doTearDown() {

    super.doTearDown();
    TestUtil.logout();
  }

  /**
   * Tests if the {@link OrderPositionState} is persisted correctly. The test modifies the {@link OrderPositionState} as
   * well as the drinkState {@link ProductOrderState}. The test focuses on saving {@link OrderPositionEto} saving and
   * verification of state change. Test data is created using Cobigen generated builders.
   */
  @Test
  public void testOrderPositionStateChange() {

    try {
      // given
      OrderEto sampleOrder = SampleCreator.createSampleOrderEto();
      OrderEto respnseOrder = this.salesManagement.saveOrder(sampleOrder);
      OrderPositionEto sampleOrderPosition = SampleCreator.createSampleOrderPositionEto(respnseOrder.getId());
      OrderPositionEto responseOrderPosition = this.salesManagement.saveOrderPosition(sampleOrderPosition);
      assertThat(responseOrderPosition).isNotNull();
      responseOrderPosition.setState(OrderPositionState.ORDERED);
      responseOrderPosition.setDrinkState(ProductOrderState.ORDERED);

      OrderPositionEto updatedOrderPosition = this.salesManagement.saveOrderPosition(sampleOrderPosition);
      assertThat(updatedOrderPosition.getState()).isEqualTo(OrderPositionState.ORDERED);

      // when
      updatedOrderPosition.setState(OrderPositionState.PREPARED);
      updatedOrderPosition.setDrinkState(ProductOrderState.PREPARED);
      updatedOrderPosition = this.salesManagement.saveOrderPosition(updatedOrderPosition);

      // then
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

  /**
   * Injects {@link DbTestHelper}.
   */
  @Inject
  public void setDbTestHelper(DbTestHelper dbTestHelper) {

    this.dbTestHelper = dbTestHelper;
  }
}
