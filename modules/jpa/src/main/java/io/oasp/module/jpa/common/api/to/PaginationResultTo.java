package io.oasp.module.jpa.common.api.to;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;

import io.oasp.module.basic.common.api.to.AbstractTo;

/**
 * Pagination information about a paginated query.
 *
 * @deprecated has been moved to {@link io.oasp.module.basic.common.api.to.PaginationResultTo oasp-basic module}. Please
 *             use the new implementation in favor of this unsupported one.
 */
@Deprecated
public class PaginationResultTo extends AbstractTo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getSize() */
  private Integer size;

  /** @see #getPage() */
  private int page = 1;

  /** @see #getTotal() */
  private Long total;

  /**
   * The constructor.
   */
  public PaginationResultTo() {
    super();
  }

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

  @Override
  protected void toString(StringBuilder buffer) {

    super.toString(buffer);
    buffer.append("@page=");
    buffer.append(this.page);
    if (this.size != null) {
      buffer.append(", size=");
      buffer.append(this.size);
    }
    if (this.total != null) {
      buffer.append(", total=");
      buffer.append(this.total);
    }
  }

}
