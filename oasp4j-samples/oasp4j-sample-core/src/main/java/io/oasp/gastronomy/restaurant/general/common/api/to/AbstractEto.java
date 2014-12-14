package io.oasp.gastronomy.restaurant.general.common.api.to;

import net.sf.mmm.util.transferobject.api.EntityTo;

/**
 * Abstract base class for an <em>{@link EntityTo entity transfer-object}</em> in this application.
 *
 * @author hohwille
 * @author erandres
 */
public abstract class AbstractEto extends EntityTo<Long> {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public AbstractEto() {

    super();
  }

}
