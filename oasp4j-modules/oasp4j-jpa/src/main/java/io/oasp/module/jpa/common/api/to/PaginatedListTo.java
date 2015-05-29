package io.oasp.module.jpa.common.api.to;

import java.util.List;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.transferobject.api.CompositeTo;
import net.sf.mmm.util.transferobject.api.TransferObject;

/**
 * A paginated list of objects with additional pagination information.
 *
 * @param <E> is the generic type of the objects. Will usually be a {@link PersistenceEntity persistent entity} when
 *        used in the data layer, or a {@link TransferObject transfer object}.
 *
 * @author henning
 */
public class PaginatedListTo<E> extends CompositeTo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getPagination() */
  private PaginationResultTo pagination;

  /** @see #getResult() */
  private List<E> result;

  /**
   * A convenience constructor which accepts a paginated list and {@link PaginationResultTo pagination information}.
   *
   * @param result is the list of objects.
   * @param pagination is the {@link PaginationResultTo pagination information}.
   */
  public PaginatedListTo(List<E> result, PaginationResultTo pagination) {

    this.result = result;
    this.pagination = pagination;
  }

  /**
   * @return the list of objects.
   */
  public List<E> getResult() {

    return this.result;
  }

  /**
   * @return pagination is the {@link PaginationResultTo pagination information}.
   */
  public PaginationResultTo getPagination() {

    return this.pagination;
  }

}
