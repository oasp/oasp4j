package io.oasp.module.jpa.dataaccess.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Workaround if forced to allow more IN-clause values than supported by your database if really required. Please use
 * this feature with extreme caution. Databases do have their reasons for such limitations. Using to many values might
 * also kill your statement cache in the database.
 *
 * @since 3.0.0
 */
class DatabaseWorkaround {

  private static final Logger LOG = LoggerFactory.getLogger(DatabaseWorkaround.class);

  private static DatabaseWorkaround instance;

  private final int maxSizeOfInClause;

  /** Die maximale Anzahl von Werten, welche Oracle in einer IN-Klausel verarbeiten kann. */
  public static final int MAX_SIZE_OF_IN_CLAUSE_IN_ORACLE = 1000;

  /**
   * The constructor.
   */
  public DatabaseWorkaround() {

    this(Integer.MAX_VALUE);
  }

  /**
   * The constructor.
   *
   * @param maxSizeOfInClause the
   */
  public DatabaseWorkaround(int maxSizeOfInClause) {

    super();
    this.maxSizeOfInClause = maxSizeOfInClause;
    if (instance == null) {
      instance = this;
    } else {
      LOG.warn("Instance {} has already been registered and can not be replaced by {}", instance, this);
    }
  }

  public int getMaxSizeOfInClause() {

    return this.maxSizeOfInClause;
  }

  /**
   * @return instance
   */
  public static DatabaseWorkaround getInstance() {

    if (instance == null) {
      new DatabaseWorkaround();
    }
    return instance;
  }

  @Override
  public String toString() {

    return getClass().getSimpleName() + "[maxSizeOfInClause=" + this.maxSizeOfInClause + "]";
  }

}
