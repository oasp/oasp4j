package io.oasp.gastronomy.restaurant.general.dataaccess.base;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;

import java.math.BigDecimal;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * This is the {@link AttributeConverter} to allow that JPA vendors can persist instances of {@link Money} if used in
 * {@link io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity entities}.
 *
 */
@Converter(autoApply = true)
public class MoneyAttributeConverter implements AttributeConverter<Money, BigDecimal> {

  /**
   * The constructor.
   */
  public MoneyAttributeConverter() {

    super();
  }

  @Override
  public BigDecimal convertToDatabaseColumn(Money attribute) {

    if (attribute == null) {
      return null;
    }
    return attribute.getValue();
  }

  @Override
  public Money convertToEntityAttribute(BigDecimal dbData) {

    if (dbData == null) {
      return null;
    }
    return new Money(dbData);
  }

}
