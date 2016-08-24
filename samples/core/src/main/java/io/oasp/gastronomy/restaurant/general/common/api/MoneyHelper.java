package io.oasp.gastronomy.restaurant.general.common.api;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;

import java.math.BigDecimal;
import java.util.List;

/**
 * This class is a little helper to deal with {@link Money}. It provides convenience methods to get a {@link Money}.
 *
 */
public final class MoneyHelper {

  /**
   * String representation of the default currency Euro (ISO 4217).
   */
  public static final String DEFAULT_CURRENCY_STRING = "EUR";

  /**
   * MonetaryAmount with amount 0 and default currency.
   */
  public static final Money ZERO_MONEY = Money.ZERO;

  /**
   * Construction prohibited.
   */
  private MoneyHelper() {

    super();
  }

  /**
   * Returns a {@link Money} with the default currency and the given amount.
   *
   * @param amount of the {@link Money}
   * @return {@link Money} with default currency
   */
  public static Money getMoneyWithDefaultCurrency(Number amount) {

    BigDecimal value;
    if (amount instanceof BigDecimal) {
      value = (BigDecimal) amount;
    } else {
      value = BigDecimal.valueOf(amount.doubleValue());
    }
    return new Money(value);
  }

  /**
   *
   * Returns a {@link Money} with the given currency and the given amount.
   *
   * @param amount amount of the {@link Money}
   * @param currencyCode ISO 4217 Code of the currency
   * @return {@link Money} with given currency
   */
  public static Money getMoney(Number amount, String currencyCode) {

    return getMoneyWithDefaultCurrency(amount);
  }

  /**
   * Sums a list of {@link Money}s. The returned {@link Money} has the currency of the {@link Money}s in the given list.
   * If the list is empty the {@link #ZERO_MONEY} is returned.
   *
   * @param moneyToSum list of {@link Money}s to sum
   * @return sum of given {@link Money}s, {@link #ZERO_MONEY} if list is empty
   */
  public static Money sumMoneys(List<Money> moneyToSum) {

    Money sum = null;

    for (Money monetaryAmount : moneyToSum) {
      if (sum == null) {
        sum = monetaryAmount;
      } else {
        sum = sum.add(monetaryAmount);
      }
    }

    if (sum == null) {
      sum = ZERO_MONEY;
    }

    return sum;
  }

}
