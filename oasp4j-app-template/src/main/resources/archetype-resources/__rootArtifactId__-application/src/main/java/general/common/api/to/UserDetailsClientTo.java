#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.general.common.api.to;

import ${package}.general.common.api.UserProfile;
import ${package}.general.common.api.datatype.Role;

/**
 * This is the {@link AbstractTo TO} for the client view on the user details.
 *
 * @author hohwille
 */
public class UserDetailsClientTo extends AbstractTo implements UserProfile {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

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

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFirstName() {

    return this.firstName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLastName() {

    return this.lastName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Role getRole() {

    return this.role;
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
