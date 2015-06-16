package io.oasp.module.jpa.common.api.to;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.transferobject.api.AbstractTransferObject;

/**
 * Pagination information about a paginated query.
 *
 * @author henning
 */
public class PaginationResultTo extends AbstractTransferObject {

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
}
