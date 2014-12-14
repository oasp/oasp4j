package io.oasp.gastronomy.restaurant.general.common.api.to;

import net.sf.mmm.util.search.base.AbstractSearchCriteria;
import net.sf.mmm.util.transferobject.api.TransferObject;

/**
 * This is the interface for a {@link net.sf.mmm.util.transferobject.api.TransferObject transfer-object } with the
 * criteria for a search query. Such object specifies the criteria selecting which hits will match when performing a
 * search.<br/>
 * <b>NOTE:</b><br/>
 * This interface only holds generic settings for the query such as {@link #getHitOffset()},
 * {@link #getMaximumHitCount()}, and {@link #getSearchTimeout()}. For your individual search, you extend
 * {@link SearchCriteriaTo} to create a java bean with all the fields for your search.
 *
 * @author hohwille
 */
public abstract class SearchCriteriaTo extends AbstractSearchCriteria implements TransferObject {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public SearchCriteriaTo() {

    super();
  }

  /**
   * Limits the {@link #getMaximumHitCount() maximum hit count} by the given <code>limit</code>. If current
   * {@link #getMaximumHitCount() maximum hit count} is <code>null</code> or greater than the given <code>limit</code>,
   * the value is replaced by <code>limit</code>.
   *
   * @param limit is the maximum allowed value for {@link #getMaximumHitCount() maximum hit count}.
   */
  public void limitMaximumHitCount(int limit) {

    Integer max = getMaximumHitCount();
    if ((max == null) || (max.intValue() > limit)) {
      setMaximumHitCount(Integer.valueOf(limit));
    }
  }
}
