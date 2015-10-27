package io.oasp.module.jpa.common.api.to;

import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.transferobject.api.AbstractTransferObject;
import net.sf.mmm.util.transferobject.api.TransferObject;
import net.sf.mmm.util.transferobject.api.TransferObjectUtil;

/**
 * Transfer object to transmit order criteria
 */
public class OrderByTo implements TransferObject, Cloneable {

  private static final long serialVersionUID = 1L;

  private String name;

  private OrderDirection direction;

  /**
   * The constructor.
   */
  public OrderByTo() {

    super();
  }

  /**
   * Returns the field 'name'.
   *
   * @return Value of name
   */
  public String getName() {

    return this.name;
  }

  /**
   * Sets the field 'name'.
   *
   * @param name New value for name
   */
  public void setName(String name) {

    this.name = name;
  }

  /**
   * Returns the field 'direction'.
   *
   * @return Value of direction
   */
  public OrderDirection getDirection() {

    return this.direction;
  }

  /**
   * Sets the field 'direction'.
   *
   * @param direction New value for direction
   */
  public void setDirection(OrderDirection direction) {

    this.direction = direction;
  }

  /**
   * {@inheritDoc}
   *
   * <b>ATTENTION:</b><br>
   * For being type-safe please use {@link TransferObjectUtil#clone(AbstractTransferObject)} instead.
   */
  @Override
  public SearchCriteriaTo clone() {

    try {
      return (SearchCriteriaTo) super.clone();
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
