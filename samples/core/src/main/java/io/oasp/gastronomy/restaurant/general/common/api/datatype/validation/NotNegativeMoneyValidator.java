package io.oasp.gastronomy.restaurant.general.common.api.datatype.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;

/**
 * Validates that an instance of Money must not have a negative price.
 *
 */
public class NotNegativeMoneyValidator implements ConstraintValidator<NotNegativeMoney, Money> {

  @Override
  public void initialize(NotNegativeMoney constraintAnnotation) {

  }

  @Override
  public boolean isValid(Money value, ConstraintValidatorContext context) {

    if (value == null) {
      return true;
    }
    return value.getValue().compareTo(BigDecimal.ZERO) >= 0;
  }

}
