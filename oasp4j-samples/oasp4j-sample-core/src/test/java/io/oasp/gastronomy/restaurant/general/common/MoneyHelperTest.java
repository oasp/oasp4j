package io.oasp.gastronomy.restaurant.general.common;

import io.oasp.gastronomy.restaurant.general.common.api.MoneyHelper;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class tests the {@link MoneyHelper}. Especially summing a List of @Link {@link Money}s.
 *
 * @author mvielsac
 */
public class MoneyHelperTest extends Assert {

  // /**
  // * Test for summing up {@link Money}s with other currency (USD) then default (EUR).
  // */
  // @Test
  // public void sumTestUSDList() {
  //
  // List<Money> moneyList = new ArrayList<>();
  //
  // double[] amounts = { 10.0, 15.2, 47.11, 42.0 };
  // double sum = 0.0;
  //
  // for (double amount : amounts) {
  // moneyList.add(MoneyHelper.getMoney(amount, "USD"));
  // sum += amount;
  // }
  //
  // Money summedAmount = MoneyHelper.sumMoneys(moneyList);
  //
  // assertEquals(MoneyHelper.getMoney(sum, "USD"), summedAmount);
  //
  // }

  /**
   * Test behavior when passing an empty list.
   */
  @Test
  public void sumTestEmptyList() {

    List<Money> moneyList = new ArrayList<>();
    Money summedAmount = MoneyHelper.sumMoneys(moneyList);
    assertEquals(MoneyHelper.ZERO_MONEY, summedAmount);
  }

  /**
   * Test for summing up {@link Money}s with default currency (EUR).
   */
  @Test
  public void sumTestEURList() {

    List<Money> moneyList = new ArrayList<>();

    double[] amounts = { 15.28, 11.47, 4.20 };
    double sum = 0.0;

    for (double amount : amounts) {
      moneyList.add(MoneyHelper.getMoneyWithDefaultCurrency(amount));
      sum += amount;
    }

    Money summedAmount = MoneyHelper.sumMoneys(moneyList);

    assertEquals(MoneyHelper.getMoney(sum, "EUR"), summedAmount);
  }

}
