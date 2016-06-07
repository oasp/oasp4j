package io.oasp.gastronomy.restaurant.general.batch.api;

import java.beans.PropertyEditorSupport;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.OfferState;

/**
 * TODO sroeger This type ...
 *
 * @author sroeger
 * @since dev
 */
public class OfferStateEditor extends PropertyEditorSupport {

  @Override
  public void setAsText(String text) {

    int textInt = Integer.parseInt(text);
    String offerState = "null";
    switch (textInt) {
    case 0:
      offerState = OfferState.NORMAL.toString();
      break;
    case 1:
      offerState = OfferState.SOLDOUT.toString();
      break;
    case 2:
      offerState = OfferState.SPECIAL.toString();
      break;
    }

    setValue(offerState);
  }
}
