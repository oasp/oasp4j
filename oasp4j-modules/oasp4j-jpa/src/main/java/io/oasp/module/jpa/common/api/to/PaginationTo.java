package io.oasp.module.jpa.common.api.to;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.transferobject.api.AbstractTransferObject;
import net.sf.mmm.util.transferobject.api.TransferObject;
import net.sf.mmm.util.transferobject.api.TransferObjectUtil;

/**
 * A {@link net.sf.mmm.util.transferobject.api.TransferObject transfer-object } containing criteria for paginating
 * queries.
 *
 * @author henning
 */
public class PaginationTo implements TransferObject, Cloneable {

  /**
   * Empty {@link PaginationTo} indicating no pagination.
   */
  public static final PaginationTo NO_PAGINATION = new PaginationTo();

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getSize() */
  private Integer size;

  /** @see #getPage() */
  private int page = 1;

  /** @see #isTotal() */
  private boolean total;

  /**
   * @return size the size of a page.
   */
  public Integer getSize() {

    return this.size;
  }

  /**
   * @param size the size of a page.
   */
  public void setSize(Integer size) {

    this.size = size;
  }

  /**
   * @return page the current page.
   */
  public int getPage() {

    return this.page;
  }

  /**
   * @param page the current page. Must be greater than 0.
   */
  public void setPage(int page) {

    if (page <= 0) {
      throw new NlsIllegalArgumentException(page, "page");
    }
    this.page = page;
  }

  /**
   * @return total is {@code true} if the client requests that the server calculates the total number of entries found.
   */
  public boolean isTotal() {

    return this.total;
  }

  /**
   * @param total is {@code true} to request calculation of the total number of entries.
   */
  public void setTotal(boolean total) {

    this.total = total;
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
