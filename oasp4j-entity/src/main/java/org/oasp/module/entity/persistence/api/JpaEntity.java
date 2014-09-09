package org.oasp.module.entity.persistence.api;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.oasp.module.entity.common.api.AbstractEntity;

/**
 * Abstract Entity for all persistent entities.
 *
 * @author hohwille
 */
@MappedSuperclass
public abstract class JpaEntity extends AbstractEntity implements PersistenceEntity {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public JpaEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public Long getId() {

    return super.getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Version
  public int getVersion() {

    return super.getVersion();
  }

}
