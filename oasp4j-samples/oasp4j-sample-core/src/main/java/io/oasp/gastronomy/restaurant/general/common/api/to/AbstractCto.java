package io.oasp.gastronomy.restaurant.general.common.api.to;

import net.sf.mmm.util.transferobject.api.AbstractTransferObject;
import net.sf.mmm.util.transferobject.api.TransferObject;

/**
 * This is the abstract base class for a composite {@link AbstractTo transfer-object}. Such object should contain
 * (aggregate) other {@link AbstractTransferObject}s but no atomic data. This means it has properties that contain a
 * {@link TransferObject} or a {@link java.util.Collection} of those but no {@link net.sf.mmm.util.lang.api.Datatype
 * values}. <br>
 * Classes extending this class should carry the suffix <code>Cto</code>.
 *
 * @author hohwille
 */
public abstract class AbstractCto extends AbstractTo {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public AbstractCto() {

    super();
  }

<<<<<<< HEAD
  /**
   * {@inheritDoc}
   *
   * <b>ATTENTION:</b><br>
   * For being type-safe please use {@link TransferObjectUtil#clone(AbstractTransferObject)} instead.
   */
  @Override
  public AbstractCto clone() {

    try {
      return (AbstractCto) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
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
