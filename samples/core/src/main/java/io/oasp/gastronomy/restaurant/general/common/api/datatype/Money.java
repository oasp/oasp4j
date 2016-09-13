package io.oasp.gastronomy.restaurant.general.common.api.datatype;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;

/**
 * This is the implementation of a {@link net.sf.mmm.util.lang.api.Datatype} to represent an amount of money.<br/>
 * We recommend to use JSR354 (<code>javax.money.MonetaryAmount</code>) instead. However, we created this when the JSR
 * was still in progress and the API had a licensing model that incompatible with ASL 2.0.
 *
 */
public class Money extends AbstractSimpleDatatype<BigDecimal> implements Comparable<Object> {

  /** A {@link Money} instance where the {@link #getValue() amount} is <code>0</code>. */
  public static final Money ZERO = new Money(BigDecimal.ZERO);

  /**
   * For simplicity we use a fixed currency. This appears reasonable for a restaurant as you would only accept money is
   * the local currency. However, be aware that even in such case a variable currency can make sense if you consider
   * that the currency of a country can change (for instance when the Euro was introduced). In such case the historical
   * money amounts in storage still have to be loaded and interpreted in the correct currency. Therefore, using
   * <code>javax.money.MonetaryAmount</code></code> instead would make sense.<br/>
   * In case you would like to change the currency to use the restaurant in a different country, you have to change it
   * here but also in the JS client.
   */
  private static final Currency CURRENCY = Currency.getInstance("EUR");

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param value is the {@link #getValue() amount}.
   */
  public Money(BigDecimal value) {

    super(value);
    Objects.requireNonNull(value, "value");
  }

  /**
   *
   * The constructor.
   */
  public Money() {

    super();
  }

  /**
   * The constructor.
   *
   * @param value is the {@link #getValue() amount}.
   */
  public Money(Number value) {

    this(BigDecimal.valueOf(value.doubleValue()));
  }

  /**
   * @return the currency the {@link Money} is represented in.
   */
  public Currency getCurrency() {

    return CURRENCY;
  }

  /**
   * @param money is the {@link Money} to add.
   * @return the sum of this {@link Money} and the given <code>money</code>.
   */
  public Money add(Money money) {

    return new Money(getValue().add(money.getValue()));
  }

  @Override
  public String toString() {

    return getValue().toPlainString() + " " + getCurrency();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(Object o) {

    Money other = (Money) o;
    return getValue().compareTo(other.getValue());
  }

}
