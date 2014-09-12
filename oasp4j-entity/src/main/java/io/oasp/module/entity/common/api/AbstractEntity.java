package io.oasp.module.entity.common.api;


/**
 * Abstract Entity for all persistent entities.
 *
 * @author hohwille
 */
public abstract class AbstractEntity implements Entity {

  private static final long serialVersionUID = 1L;

  /** @see #getId() */
  private Long id;

  /** @see #getVersion() */
  private int version;

  /**
   * The constructor.
   */
  public AbstractEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Long getId() {

    return this.id;
  }

  /**
   * @param id is the new {@link #getId() ID}.
   */
  public void setId(Long id) {

    this.id = id;
  }

  /**
   * {@inheritDoc}
   */
  public int getVersion() {

    return this.version;
  }

  /**
   * @param version is the new {@link #getVersion() version}.
   */
  public void setVersion(int version) {

    this.version = version;
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
  }

}