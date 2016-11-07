package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.MenuItem;
import io.oasp.module.basic.common.api.to.AbstractEto;

/**
 * The {@link AbstractEto ETO} for a {@link MenuItem}.
 *
 */
public abstract class MenuItemEto extends AbstractEto implements MenuItem {

  private static final long serialVersionUID = 1L;

  private String name;

  private String description;

  /**
   * The constructor.
   */
  public MenuItemEto() {

    super();
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public void setName(String name) {

    this.name = name;
  }

  @Override
  public String getDescription() {

    return this.description;
  }

  @Override
  public void setDescription(String description) {

    this.description = description;
  }
}
