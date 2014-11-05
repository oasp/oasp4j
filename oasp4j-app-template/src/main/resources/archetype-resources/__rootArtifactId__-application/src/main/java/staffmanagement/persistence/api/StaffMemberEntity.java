#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.staffmanagement.persistence.api;

import ${package}.general.common.api.datatype.Role;
import ${package}.general.persistence.api.ApplicationPersistenceEntity;
import ${package}.staffmanagement.common.api.StaffMember;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * {@link ApplicationPersistenceEntity Entity} representing an {@link StaffMember}.
 *
 * @author loverbec
 */
@Entity(name = "StaffMember")
public class StaffMemberEntity extends ApplicationPersistenceEntity implements StaffMember {

  private static final long serialVersionUID = 1L;

  private String name;

  private String firstName;

  private String lastName;

  private Role role;

  /**
   * The constructor.
   */
  public StaffMemberEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Column(name = "login", unique = true)
  @Override
  public String getName() {

    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setName(String login) {

    this.name = login;
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
  public void setFirstName(String firstName) {

    this.firstName = firstName;
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
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Role getRole() {

    return this.role;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRole(Role role) {

    this.role = role;
  }

}
