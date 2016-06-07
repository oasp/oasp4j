package io.oasp.gastronomy.restaurant.general.batch.api;

import java.beans.PropertyEditorSupport;
import java.text.DecimalFormat;
import java.text.ParsePosition;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;

/**
 * TODO sroeger This type ...
 *
 * @author sroeger
 * @since dev
 */
public class MoneyEditor extends PropertyEditorSupport {

  @Override
  public void setAsText(String text) {

    DecimalFormat decimalFormat = new DecimalFormat();
    decimalFormat.setParseBigDecimal(true);

    setValue(new Money(decimalFormat.parse(text, new ParsePosition(0))));
  }
}
