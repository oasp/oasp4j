package io.oasp.module.rest.service.api;

import net.sf.mmm.util.search.api.SearchCriteria;
import net.sf.mmm.util.search.base.AbstractSearchCriteria;

/**
 * This type is a wrapper for paging parameters used in REST API. Provides also convenient conversion to
 * {@link SearchCriteria} query.
 *
 * @author llaszkie
 */
public class PagingParameters {

  /**
   * An empty parameters indicating no paging in request.
   */
  public static final PagingParameters NO_PAGING = new PagingParameters();

  private static final int DEFAULT_MAX_RESULT_PER_PAGE = 100;

  private boolean off;

  private int pageNumber;

  private int count;

  /**
   * The constructor.
   *
   * @param pageNumber number of the requested page (1-based index)
   * @param count limit of entries for one page. if <code>null</code> default value will be used
   */
  public PagingParameters(int pageNumber, Integer count) {

    super();
    this.pageNumber = pageNumber;
    this.count = count != null ? count : DEFAULT_MAX_RESULT_PER_PAGE;
  }

  /**
   * The constructor. Indicates NO paging.
   */
  public PagingParameters() {

    super();
    this.off = true;
  }

  /**
   * @return <code>true</code> if NO paging was requested
   */
  public boolean isOff() {

    return this.off;
  }

  /**
   * Arms provided criteria with held paging parameters unless paging parameters were NOT provided.
   *
   * @param criteria criteria to be extended.
   */
  public void extendIfRequested(AbstractSearchCriteria criteria) {

    if (!this.off) {
      criteria.setHitOffset((this.pageNumber - 1) * this.count);
      criteria.setMaximumHitCount(this.count);
    }
  }
}