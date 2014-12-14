package io.oasp.gastronomy.restaurant.general.common.api.to;

import net.sf.mmm.util.transferobject.api.AbstractTransferObject;

/**
 * Abstract class for a plain {@link net.sf.mmm.util.transferobject.api.TransferObject} that is neither a
 * {@link AbstractEto ETO} nor a {@link AbstractCto CTO}.
 *
 * @author hohwille
 */
public class AbstractTo extends AbstractTransferObject {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public AbstractTo() {

    super();
  }

}
