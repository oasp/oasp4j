#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.persistence.base;

import ${package}.general.common.api.datatype.Money;

import java.math.BigDecimal;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * This is the {@link AttributeConverter} to allow that JPA vendors can persist instances of {@link Money} if used in
 * {@link ${package}.general.common.api.ApplicationEntity entities}.
 *
 * @author hohwille
 */
@Converter(autoApply = true)
public class MoneyAttributeConverter implements AttributeConverter<Money, BigDecimal> {

  /**
   * The constructor.
   */
  public MoneyAttributeConverter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal convertToDatabaseColumn(Money attribute) {

    if (attribute == null) {
      return null;
    }
    return attribute.getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Money convertToEntityAttribute(BigDecimal dbData) {

    if (dbData == null) {
      return null;
    }
    return new Money(dbData);
  }

}
