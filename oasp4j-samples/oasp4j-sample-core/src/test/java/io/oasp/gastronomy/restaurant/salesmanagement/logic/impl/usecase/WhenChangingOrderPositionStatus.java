package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.module.test.common.api.category.CategoryModuleTest;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This is the test-case of {@link UcManageOrderPositionImpl}.
 *
 * @author mgoebel
 */
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "/testdata/WhenChangingOrderPositionStatus.csv")
public class WhenChangingOrderPositionStatus implements CategoryModuleTest {

  @Steps
  OrderPositionSteps orderPositionSteps;

  private String oldStatus;

  private String newStatus;

  private String expectedResult;

  @Test
  public void shouldValidateStatusTransition() {

    this.orderPositionSteps.anOrderPositionThatIsInState(this.oldStatus);
    this.orderPositionSteps.theUserTriesToChangeStateTo(this.newStatus);
    this.orderPositionSteps.theValidationResultShouldBe(this.expectedResult);
  }

}