package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.Product;

/**
 * The {@link io.oasp.module.basic.common.api.to.AbstractEto ETO} for a {@link Product}.
 *
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

  @Override
  public void setPictureId(Long binaryObjectId) {

    this.pictureId = binaryObjectId;
  }

  @Override
  public Long getPictureId() {

    return this.pictureId;
  }

}
