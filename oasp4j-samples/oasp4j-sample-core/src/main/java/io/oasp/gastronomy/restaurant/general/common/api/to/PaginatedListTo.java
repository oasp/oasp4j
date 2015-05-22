package io.oasp.gastronomy.restaurant.general.common.api.to;

import io.oasp.module.jpa.common.api.to.PaginationResultTo;

import java.util.List;

import net.sf.mmm.util.transferobject.api.AbstractTransferObject;
import net.sf.mmm.util.transferobject.api.TransferObject;
import net.sf.mmm.util.transferobject.base.TransferObjectUtilImpl;

/**
 * A list of {@link AbstractTransferObject transfer objects} with additional pagination information.
 *
 * @param <I> is the generic type of the {@link AbstractTransferObject transfer objects}
 *
 * @author henning
 */
public class PaginatedListTo<I extends TransferObject> extends AbstractTo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private PaginationResultTo pagination;

  private List<I> result;

  /**
   * The constructor.
   */
  public PaginatedListTo() {

    super();
  }

  /**
   * A convenience constructor which accepts the paginated list and clones the passed {@code pagination} information.
   *
   * @param result is the list of {@link AbstractTransferObject transfer objects}.
   * @param pagination is the pagination information. This object will be cloned before setting it.
   */
  public PaginatedListTo(List<I> result, PaginationResultTo pagination) {

    super();

    this.setResult(result);
    this.setPagination(TransferObjectUtilImpl.getInstance().clone(pagination));
  }

  /**
   * @return the list of {@link AbstractTransferObject transfer objects}.
   */
  public List<I> getResult() {

    return this.result;
  }

  /**
   * @param result is the list of {@link AbstractTransferObject transfer objects}.
   */
  public void setResult(List<I> result) {

    this.result = result;
  }

  /**
   * @return the {@link PaginationResultTo pagination information}.
   */
  public PaginationResultTo getPagination() {

    return this.pagination;
  }

  /**
   * @param pagination the {@link PaginationResultTo pagination information} to set
   */
  public void setPagination(PaginationResultTo pagination) {

    this.pagination = pagination;
  }

}
