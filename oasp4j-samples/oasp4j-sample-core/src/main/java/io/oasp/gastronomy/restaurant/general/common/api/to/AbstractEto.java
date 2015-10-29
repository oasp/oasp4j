package io.oasp.gastronomy.restaurant.general.common.api.to;

public abstract class AbstractEto extends AbstractTo implements MutableRevisionedEntity<Long> {

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
<<<<<<< HEAD
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
=======
>>>>>>> afc5c448867bf6556fe5d709ac09077e988bd93d
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
<<<<<<< HEAD
  protected void toString(StringBuilder buffer) {

    buffer.append(getClass().getSimpleName());
=======
  @Override
  protected void toString(StringBuilder buffer) {

    super.toString(buffer);
>>>>>>> afc5c448867bf6556fe5d709ac09077e988bd93d
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
