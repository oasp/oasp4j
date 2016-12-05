package io.oasp.module.jpa.dataaccess.api;

import javax.persistence.Column;

/**
 * TODO vkiran This type ...
 *
 * @author vkiran
 */
public class AdvancedRevisionEntiryOracle extends AdvancedRevisionEntity {
  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  @Override
  @Column(name = "\"user\"")
  public void setUser(String user) {

    super.setUser(user);
  }
}