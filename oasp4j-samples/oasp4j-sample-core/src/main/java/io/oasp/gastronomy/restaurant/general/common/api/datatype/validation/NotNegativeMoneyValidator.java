package io.oasp.gastronomy.restaurant.general.common.api.datatype.validation;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates that an instance of Money must not have a negative price.
 *
 * @author jmetzler
 */
public class NotNegativeMoneyValidator implements ConstraintValidator<NotNegativeMoney, Money> {

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(NotNegativeMoney constraintAnnotation) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid(Money value, ConstraintValidatorContext context) {

    return value.getValue().compareTo(BigDecimal.ZERO) >= 0;
  }

}
