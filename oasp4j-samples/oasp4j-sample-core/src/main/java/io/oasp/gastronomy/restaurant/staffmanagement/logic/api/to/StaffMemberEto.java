package io.oasp.gastronomy.restaurant.staffmanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.general.common.api.to.AbstractEto;
import io.oasp.gastronomy.restaurant.staffmanagement.common.api.StaffMember;

import java.util.Locale;

/**
 * The {@link AbstractEto ETO} for a {@link StaffMember}.
 *
 * @author hohwille
 * @author etomety
 */
public class StaffMemberEto extends AbstractEto implements StaffMember {

  // UUID
  private static final long serialVersionUID = 1L;

  private String name;

  private String firstName;

  private String lastName;

  private Role role;

  private Locale language;

  /**
   * The constructor.
   */
  public StaffMemberEto() {

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
  public void setName(String name) {

    this.name = name;
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

  /**
   * @return language
   */
  public Locale getLanguage() {

    return this.language;
  }

  /**
   * @param language the language to set
   */
  public void setLanguage(Locale language) {

    this.language = language;
  }
}
