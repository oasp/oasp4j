package io.oasp.gastronomy.restaurant.offermanagement.common.api.exception;

import io.oasp.gastronomy.restaurant.general.common.api.NlsBundleApplicationRoot;
import io.oasp.gastronomy.restaurant.general.common.api.exception.ApplicationBusinessException;

/**
 * Issued if a product could not be found, which was mandatory for further processing.
 *
 * @author mbrunnli
 * @since dev
 */
public class ProductNotFoundException extends ApplicationBusinessException {

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param productId ID of the product, which could not be found.
   */
  public ProductNotFoundException(Long productId) {

    super(createBundle(NlsBundleApplicationRoot.class).productNotFound(productId));
  }

}
