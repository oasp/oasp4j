package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.Product;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto ETO} for a {@link Product}.
 *
 * @author hohwille
 * @author jozitz
 */
public abstract class ProductEto extends MenuItemEto implements Product {

  private static final long serialVersionUID = 1L;

  private Long pictureId;

  /**
   * Constructor.
   */
  public ProductEto() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPictureId(Long binaryObjectId) {

    this.pictureId = binaryObjectId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getPictureId() {

    return this.pictureId;
  }

}
