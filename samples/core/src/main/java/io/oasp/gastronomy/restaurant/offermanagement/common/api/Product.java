package io.oasp.gastronomy.restaurant.offermanagement.common.api;

import io.oasp.gastronomy.restaurant.general.logic.api.to.BinaryObjectEto;

/**
 * This is the interface for a {@link Product} of the restaurant.
 *
 */
public interface Product extends MenuItem {

  /**
   * @return the ID of a {@link BinaryObjectEto} that represents the picture
   */
  Long getPictureId();

  /**
   * @param binaryObjectId the ID of a {@link BinaryObjectEto} that represents the picture
   */
  void setPictureId(Long binaryObjectId);

}
