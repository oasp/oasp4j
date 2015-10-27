package io.oasp.module.jpa.common.api.to;

import java.util.List;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.transferobject.api.AbstractTransferObject;
import net.sf.mmm.util.transferobject.api.TransferObject;
import net.sf.mmm.util.transferobject.api.TransferObjectUtil;

/**
 * A paginated list of objects with additional pagination information.
 *
 * @param <E> is the generic type of the objects. Will usually be a {@link PersistenceEntity persistent entity} when
 *        used in the data layer, or a {@link TransferObject transfer object}.
 *
 * @author henning
 */
public class PaginatedListTo<E> implements TransferObject, Cloneable {

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
