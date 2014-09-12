/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.oasp.module.entity.common.api.transferobject;

/**
 * This is the abstract base class for a {@link TransferObject}. It already contains a small and simple infrastructure
 * for {@link #clone() cloning}, {@link #equals(Object)} and {@link #hashCode()} as well as {@link #toString()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractTransferObject implements TransferObject, Cloneable {

  /** UID for serialization. */
  private static final long serialVersionUID = -6842823766837505377L;

  /**
   * The constructor.
   */
  public AbstractTransferObject() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractTransferObject clone() {

    try {
      return (AbstractTransferObject) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    // has to be overridden by every sub-class (use Eclipse "Generate equals() and hashCode()")...
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    // has to be overridden by every sub-class (use Eclipse "Generate equals() and hashCode()")...
    return 1;
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
