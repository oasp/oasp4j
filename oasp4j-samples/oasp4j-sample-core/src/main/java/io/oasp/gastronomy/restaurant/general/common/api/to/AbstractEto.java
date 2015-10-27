package io.oasp.gastronomy.restaurant.general.common.api.to;

import io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity;

import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.transferobject.api.AbstractTransferObject;
import net.sf.mmm.util.transferobject.api.TransferObject;
import net.sf.mmm.util.transferobject.api.TransferObjectUtil;

/**
 * This is the abstract base class for an {@link TransferObject} that only contains data without relations. This is
 * called <em>DTO</em> (data transfer object). Here data means properties that typically represent a
 * {@link net.sf.mmm.util.lang.api.Datatype} and potentially for relations the ID (as {@link Long}). For actual
 * relations you will use {@link AbstractCto CTO}s to express what set of entities to transfer, load, save, update, etc.
 * without redundancies. It typically corresponds to an {@link net.sf.mmm.util.entity.api.GenericEntity entity}. For
 * additional details and an example consult the {@link net.sf.mmm.util.transferobject.api package JavaDoc}.
 *
 * @author hohwille
 * @author erandres
 */
public abstract class AbstractEto implements TransferObject, Cloneable, MutablePersistenceEntity<Long> {

  private static final long serialVersionUID = 1L;

  /** @see #getId() */
  private Long id;

  /** @see #getModificationCounter() */
  private int modificationCounter;

  /** @see #getRevision() */
  private Number revision;

  /**
   * @see #getModificationCounter()
   */
  private transient GenericEntity<Long> persistentEntity;

  /**
   * The constructor.
   */
  public AbstractEto() {

    super();
    this.revision = LATEST_REVISION;
  }

  /**
   * {@inheritDoc}
   *
   * <b>ATTENTION:</b><br>
   * For being type-safe please use {@link TransferObjectUtil#clone(AbstractTransferObject)} instead.
   */
  @Override
  public AbstractEto clone() {

    try {
      return (AbstractEto) super.clone();
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
   * {@inheritDoc}
   */
  @Override
  public Long getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(Long id) {

    this.id = id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getModificationCounter() {

    if (this.persistentEntity != null) {
      // JPA implementations will update modification counter only after the transaction has been committed.
      // Conversion will typically happen before and would result in the wrong (old) modification counter.
      // Therefore we update the modification counter here (that has to be called before serialization takes
      // place).
      this.modificationCounter = this.persistentEntity.getModificationCounter();
    }
    return this.modificationCounter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModificationCounter(int version) {

    this.modificationCounter = version;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Number getRevision() {

    return this.revision;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRevision(Number revision) {

    this.revision = revision;
  }

  /**
   * Method to extend {@link #toString()} logic.
   *
   * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(Object) append} the string
   *        representation.
   */
  protected void toString(StringBuilder buffer) {

    buffer.append(getClass().getSimpleName());
    if (this.id != null) {
      buffer.append("[id=");
      buffer.append(this.id);
      buffer.append("]");
    }
    if (this.revision != null) {
      buffer.append("[rev=");
      buffer.append(this.revision);
      buffer.append("]");
    }
  }

  /**
   * Inner class to grant access to internal {@link PersistenceEntity} reference of an {@link AbstractEto}. Shall only
   * be used internally and never be external users.
   */
  public static class PersistentEntityAccess {

    /**
     * Sets the internal {@link PersistenceEntity} reference of the given {@link AbstractEto}.
     *
     * @param <ID> is the generic type of the {@link GenericEntity#getId() ID}.
     * @param eto is the {@link AbstractEto}.
     * @param persistentEntity is the {@link PersistenceEntity}.
     */
    protected <ID> void setPersistentEntity(AbstractEto eto, PersistenceEntity<Long> persistentEntity) {

      assert ((eto.persistentEntity == null) || (persistentEntity == null));
      eto.persistentEntity = persistentEntity;
    }
  }
}
