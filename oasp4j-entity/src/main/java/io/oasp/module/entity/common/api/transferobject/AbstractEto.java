package io.oasp.module.entity.common.api.transferobject;

import io.oasp.module.entity.common.api.Entity;

/**
 * This is the abstract base class for an {@link AbstractDto DTO} corresponding to a persistent
 * {@link io.oasp.module.entity.common.api.Entity}. Classes derived from this class should carry the suffix
 * <code>Eto</code>. We recommend to generated these objects from the corresponding persistent
 * {@link io.oasp.module.entity.common.api.Entity}.<br/>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AbstractEto extends AbstractDto implements Entity {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getId() */
  private Long id;

  /** @see #getVersion() */
  private int version;

  /**
   * The constructor.
   */
  public AbstractEto() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
  @Override
  public int getVersion() {

    return this.version;
  }

  /**
   * @param version is the new {@link #getVersion() version}.
   */
  public void setVersion(int version) {

    this.version = version;
  }

}
