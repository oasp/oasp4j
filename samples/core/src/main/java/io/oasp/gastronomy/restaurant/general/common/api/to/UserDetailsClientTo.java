package io.oasp.gastronomy.restaurant.general.common.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.UserProfile;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.module.basic.common.api.to.AbstractTo;

/**
 * This is the {@link AbstractTo TO} for the client view on the user details.
 *
 */
public class UserDetailsClientTo extends AbstractTo implements UserProfile {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private Long id;

  private String name;

  private String firstName;

  private String lastName;

  private Role role;

  /**
   * The constructor.
   */
  public UserDetailsClientTo() {

    super();
  }

  @Override
  public Long getId() {

    return this.id;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public String getFirstName() {

    return this.firstName;
  }

  @Override
  public String getLastName() {

    return this.lastName;
  }

  @Override
  public Role getRole() {

    return this.role;
  }

  /**
   * Sets the ID.
   *
   * @param id the ID to set
   */
  public void setId(Long id) {

    this.id = id;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {

    this.name = name;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  /**
   * @param role the role to set
   */
  public void setRole(Role role) {

    this.role = role;
  }

}
