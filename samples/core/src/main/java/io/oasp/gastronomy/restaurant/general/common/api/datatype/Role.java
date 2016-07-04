package io.oasp.gastronomy.restaurant.general.common.api.datatype;

import java.security.Principal;

// BEGIN ARCHETYPE SKIP
/**
 * Defines the available roles of a {@link io.oasp.gastronomy.restaurant.staffmanagement.common.api.StaffMember}.
 *
 * @see io.oasp.gastronomy.restaurant.staffmanagement.common.api.StaffMember#getRole()
 *
 * @author hohwille
 * @author loverbec
 */
// END ARCHETYPE SKIP
public enum Role implements Principal {

  // BEGIN ARCHETYPE SKIP
  /**
   * AccessControlGroup of a cook who works in the kitchen and can see the orders with their positions. He prepares the
   * menus and side-dishes and can mark order-positions as prepared.
   */
  COOK("Cook"),

  /**
   * AccessControlGroup of a waiter who manages the table states, takes orders at the tables and serves drinks and the
   * prepared food from the kitchen. Further, he is responsible for billing the payments of orders, etc.
   */
  WAITER("Waiter"),

  /**
   * AccessControlGroup of a barkeeper who is responsible for taking orders for drinks at the bar as well as serving and
   * billing them.
   */
  BARKEEPER("Barkeeper"),

  /**
   * AccessControlGroup of a chief on the restaurant who can manage the master-data such as offers, products, and other
   * {@link io.oasp.gastronomy.restaurant.staffmanagement.common.api.StaffMember}s.
   */
  // END ARCHETYPE SKIP
  CHIEF("Chief");

  private final String name;

  private Role(String name) {

    this.name = name;
  }

  @Override
  public String getName() {

    return this.name;
  }
}
