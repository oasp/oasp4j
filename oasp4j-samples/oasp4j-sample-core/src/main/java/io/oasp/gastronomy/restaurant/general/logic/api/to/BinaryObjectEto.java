package io.oasp.gastronomy.restaurant.general.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.BinaryObject;
import io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto ETO} for a {@link BinaryObject}.
 *
 * @author sspielma
 */
public class BinaryObjectEto extends AbstractEto implements BinaryObject {

  private static final long serialVersionUID = 1L;

  private String mimeType;

  private long size;

  /**
   * Constructor.
   */
  public BinaryObjectEto() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMimeType(String mimeType) {

    this.mimeType = mimeType;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMimeType() {

    return this.mimeType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getSize() {

    return this.size;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(long size) {

    this.size = size;
  }

}
