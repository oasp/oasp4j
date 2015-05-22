package io.oasp.module.jpa.common.api.to;

import java.util.List;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.transferobject.api.CompositeTo;

/**
 * A list of {@link PersistenceEntity entities} with additional pagination information.
 *
 * @param <I> is the generic type of the {@link PersistenceEntity entities}.
 *
 * @author henning
 */
public class PaginatedEntityListTo<I extends PersistenceEntity<?>> extends CompositeTo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getPagination() */
  private PaginationResultTo pagination;

  /** @see #getResult() */
  private List<I> result;

  /**
   * A convenience constructor which accepts a paginated list and {@link PaginationResultTo pagination information}.
   *
   * @param result is the list of {@link PersistenceEntity entities}.
   * @param pagination is the {@link PaginationResultTo pagination information}.
   */
  public PaginatedEntityListTo(List<I> result, PaginationResultTo pagination) {

    this.result = result;
    this.pagination = pagination;
  }

  /**
   * @return the list of {@link PersistenceEntity entities}.
   */
  public List<I> getResult() {

    return this.result;
  }

  /**
   * @return pagination is the {@link PaginationResultTo pagination information}.
   */
  public PaginationResultTo getPagination() {

    return this.pagination;
  }

}
