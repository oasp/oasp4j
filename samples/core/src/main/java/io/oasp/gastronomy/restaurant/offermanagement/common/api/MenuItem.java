package io.oasp.gastronomy.restaurant.offermanagement.common.api;

import io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity;

/**
 * This is the interface for a {@link MenuItem} what is either a {@link Product} or an {@link Offer}.
 *
 */
public interface MenuItem extends ApplicationEntity {

  /**
   * @return a description with the details of this item.
   */
  String getDescription();

  /**
   * @param description is the new {@link #getDescription() description}.
   */
  void setDescription(String description);

  /**
   * @return the display name of this item.
   */
  String getName();

  /**
   * @param name is the new {@link #getName() name}.
   */
  void setName(String name);

}
