package io.oasp.module.jpa.common.api.to;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.transferobject.api.AbstractTransferObject;
import net.sf.mmm.util.transferobject.api.TransferObject;
import net.sf.mmm.util.transferobject.api.TransferObjectUtil;

/**
 * Pagination information about a paginated query.
 *
 * @author henning
 */
public class PaginationResultTo implements TransferObject {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getSize() */
  private Integer size;

  /** @see #getPage() */
  private int page = 1;

  /** @see #getTotal() */
  private Long total;

  /**
   * Constructor expecting an existing {@link PaginationTo pagination criteria} and the total number of results found.
   *
   * @param pagination is an existing {@link PaginationTo pagination criteria}.
   * @param total is the total number of results found without pagination.
   */
  public PaginationResultTo(PaginationTo pagination, Long total) {

    super();

    setPage(pagination.getPage());
    setSize(pagination.getSize());
    setTotal(total);
  }

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
   * @return total the total number of entities
   */
  public Long getTotal() {

    return this.total;
  }

  /**
   * @param total the total number of entities
   */
  public void setTotal(Long total) {

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
