package io.oasp.gastronomy.restaurant.salesmanagement.logic.impl.usecase;

import io.oasp.module.test.common.base.ModuleTest;

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
public class WhenChangingOrderPositionStatus extends ModuleTest {

  @Steps
  OrderPositionSteps orderPositionSteps;

  private String oldStatus;

  private String newStatus;

  private String expectedResult;

  @Test
  public void shouldValidateStatusTransition() {

    // Given
    this.orderPositionSteps.anOrderPositionThatIsInState(this.oldStatus);

    // When
    this.orderPositionSteps.theUserTriesToChangeStateTo(this.newStatus);

    // Then
    this.orderPositionSteps.theValidationResultShouldBe(this.expectedResult);
  }

}