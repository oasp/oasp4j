/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.oasp.module.jpa.dataaccess.api.common;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.mmm.util.entity.api.PersistenceEntity;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import io.oasp.module.jpa.dataaccess.api.AdvancedRevisionListener;

/**
 * This is a custom {@link org.hibernate.envers.DefaultRevisionEntity revision entity} also containing the actual user.
 *
 * @see org.hibernate.envers.DefaultRevisionEntity
 *
 */
@Entity
@RevisionEntity(AdvancedRevisionListener.class)
@Table(name = "RevInfo")
public class AdvancedRevisionEntity implements PersistenceEntity<Long> {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getId() */
  @Id
  @GeneratedValue
  @RevisionNumber
  private Long id;

  /** @see #getTimestamp() */
  @RevisionTimestamp
  private long timestamp;

  /** @see #getDate() */
  private transient Date date;

  /** @see #getUserName() */

  private String userName;

  /**
   * The constructor.
   */
  public AdvancedRevisionEntity() {

    super();
  }

  @Override
  public Long getId() {

    return this.id;
  }

  /**
   * @param id is the new value of {@link #getId()}.
   */
  public void setId(Long id) {

    this.id = id;
  }

  /**
   * @return the timestamp when this revision has been created.
   */
  public long getTimestamp() {

    return this.timestamp;
  }

  /**
   * @return the {@link #getTimestamp() timestamp} as {@link Date}.
   */
  public Date getDate() {

    if (this.date == null) {
      this.date = new Date(this.timestamp);
    }
    return this.date;
  }

  /**
   * @param timestamp is the new value of {@link #getTimestamp()}.
   */
  public void setTimestamp(long timestamp) {

    this.timestamp = timestamp;
  }

  /**
   * @return the login or id of the user that has created this revision.
   */

  public String getUserName() {

    return this.userName;
  }

  /**
   * @param userName is the new value of {@link #getUserName()}.
   */
  public void setUserName(String userName) {

    this.userName = userName;
  }

  @Override
  public int getModificationCounter() {

    return 0;
  }

  @Override
  public Number getRevision() {

    return null;
  }
}