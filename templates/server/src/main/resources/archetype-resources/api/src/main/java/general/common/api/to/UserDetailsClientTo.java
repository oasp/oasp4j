package ${package}.general.common.api.to;

import ${package}.general.common.api.UserProfile;
import io.oasp.module.basic.common.api.to.AbstractTo;

/**
 * This is the {@link AbstractTo TO} for the client view on the user details.
 */
public class UserDetailsClientTo extends AbstractTo implements UserProfile {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String name;

  private String firstName;

  private String lastName;

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

  /**
   * @param id the new {@link #getId() ID}.
   */
  public void setId(Long id) {

    this.id = id;
  }

  /**
   * @param name the new {@link #getName() login name}.
   */
  public void setName(String name) {

    this.name = name;
  }

  /**
   * @param firstName the new {@link #getFirstName() first name}.
   */
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  /**
   * @param lastName the new {@link #getLastName() last name}.
   */
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

}
