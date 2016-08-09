package io.oasp.gastronomy.restaurant.offermanagement.batch.impl.offerimport.writer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;

import org.springframework.batch.item.ItemProcessor;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;

/**
 * Helper class that implements {@link ItemProcessor} for converting {@link OfferCsv} into {@link OfferEto}.
 *
 * @author sroeger
 */
public class OfferItemConverter implements ItemProcessor<OfferCsv, OfferEto> {

  @Override
  public OfferEto process(OfferCsv item) throws Exception {

    OfferEto offerEto = new OfferEto();
    offerEto.setName(item.getName());
    offerEto.setDescription(item.getDescription());

    int textInt = Integer.parseInt(item.getState());
    OfferState offerState = null;
    switch (textInt) {
    case 0:
      offerState = OfferState.NORMAL;
      break;
    case 1:
      offerState = OfferState.SOLDOUT;
      break;
    case 2:
      offerState = OfferState.SPECIAL;
      break;
    }

    offerEto.setState(offerState);
    offerEto.setMealId(Long.parseLong(item.getMealId()));
    offerEto.setSideDishId(Long.parseLong(item.getSideDishId()));
    offerEto.setDrinkId(Long.parseLong(item.getDrinkId()));

    // parse String to Big Decimal to Money
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setGroupingSeparator(',');
    symbols.setDecimalSeparator('.');
    String pattern = "#,##0.0#";
    DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
    decimalFormat.setParseBigDecimal(true);

    offerEto.setPrice(new Money(decimalFormat.parse(item.getPrice(), new ParsePosition(0))));

    return offerEto;
  }
}
