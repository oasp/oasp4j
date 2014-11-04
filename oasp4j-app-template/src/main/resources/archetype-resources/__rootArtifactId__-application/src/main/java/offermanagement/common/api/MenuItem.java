#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.offermanagement.common.api;

import ${package}.general.common.api.ApplicationEntity;

/**
 * This is the interface for a {@link MenuItem} what is either a {@link Product} or an {@link Offer}.
 *
 * @author hohwille
 */
public interface MenuItem extends ApplicationEntity {

  /**
   * @return a description with the details of this item.
   */
  String getDescription();

  /**
   * @param description is the new {@link ${symbol_pound}getDescription() description}.
   */
  void setDescription(String description);

  /**
   * @return the display name of this item.
   */
  String getName();

  /**
   * @param name is the new {@link ${symbol_pound}getName() name}.
   */
  void setName(String name);

}
