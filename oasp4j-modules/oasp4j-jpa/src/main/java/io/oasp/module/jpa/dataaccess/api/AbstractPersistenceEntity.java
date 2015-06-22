package io.oasp.module.jpa.dataaccess.api;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Abstract base implementation of {@link MutablePersistenceEntity} with a {@link GeneratedValue generated}
 * {@link #getId() primary key}. In case you need a different type of key add it as extra column and make it
 * {@link javax.persistence.Column#unique() unique}.
 *
 * @author hohwille
 * @author rjoeris
 */
@MappedSuperclass
public abstract class AbstractPersistenceEntity implements MutablePersistenceEntity<Long> {

  private static final long serialVersionUID = 1L;

  /** @see #getId() */
  private Long id;

  /** @see #getModificationCounter() */
  private int modificationCounter;

  /** @see #getRevision() */
  private Number revision;

  /**
   * The constructor.
   */
  public AbstractPersistenceEntity() {

    super();
  }

  @Override
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public Long getId() {

    return this.id;
  }

  public void setId(Long id) {

    this.id = id;
  }

  @Override
  @Version
  public int getModificationCounter() {

    return this.modificationCounter;
  }

  @Override
  public void setModificationCounter(int version) {

    this.modificationCounter = version;
  }

  @Override
  @Transient
  public Number getRevision() {

    return this.revision;
  }

  /**
   * @param revision the revision to set
   */
  public void setRevision(Number revision) {

    this.revision = revision;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + this.modificationCounter;
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractPersistenceEntity other = (AbstractPersistenceEntity) obj;
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    if (this.modificationCounter != other.modificationCounter) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder();
    toString(buffer);
    return buffer.toString();
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
