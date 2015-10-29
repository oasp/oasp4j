package io.oasp.gastronomy.restaurant.general.common.api.to;

import net.sf.mmm.util.transferobject.api.TransferObject;

/**
 * Abstract class for a plain {@link net.sf.mmm.util.transferobject.api.TransferObject} that is neither a
 * {@link AbstractEto ETO} nor a {@link AbstractCto CTO}. Classes extending this class should carry the suffix
 * <code>Cto</code>. <br>
 *
 * @author hohwille
 */
public abstract class AbstractTo implements TransferObject {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public AbstractTo() {

    super();
  }

  /**
   * {@inheritDoc}
<<<<<<< HEAD
   *
   * <b>ATTENTION:</b><br>
   * For being type-safe please use {@link TransferObjectUtil#clone(AbstractTransferObject)} instead.
   */
  @Override
  public AbstractTo clone() {

    try {
      return (AbstractTo) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
=======
>>>>>>> afc5c448867bf6556fe5d709ac09077e988bd93d
   */
  @Override
  public final String toString() {

    StringBuilder buffer = new StringBuilder();
    toString(buffer);
    return buffer.toString();
  }

  /**
   * Method to extend {@link #toString()} logic. Override to add additional information.
   *
   * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(Object) append} the string
   *        representation.
   */
  protected void toString(StringBuilder buffer) {

    buffer.append(getClass().getSimpleName());
  }

}
