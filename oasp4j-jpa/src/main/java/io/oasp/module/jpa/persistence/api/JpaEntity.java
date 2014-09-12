package io.oasp.module.jpa.persistence.api;

import io.oasp.module.entity.common.api.AbstractEntity;
import io.oasp.module.entity.common.api.PersistenceEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

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
