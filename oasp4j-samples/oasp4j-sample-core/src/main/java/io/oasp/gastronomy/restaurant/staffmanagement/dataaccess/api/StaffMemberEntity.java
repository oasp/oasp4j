package io.oasp.gastronomy.restaurant.staffmanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Role;
import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.staffmanagement.common.api.StaffMember;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.LocaleUtils;

/**
 * The {@link io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity persistent entity} for
 * {@link StaffMember}.
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

  private String languageStr;

  private Locale language;

  /**
   * The constructor.
   */
  public StaffMemberEntity() {

    super();
  }

  @Column(name = "login", unique = true)
  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public void setName(String login) {

    this.name = login;
  }

  @Override
  public String getFirstName() {

    return this.firstName;
  }

  @Override
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  @Override
  public String getLastName() {

    return this.lastName;
  }

  @Override
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  @Override
  public Role getRole() {

    return this.role;
  }

  @Override
  public void setRole(Role role) {

    this.role = role;
  }

  /**
   * @return languageStr
   */
  public String getLanguageStr() {

    return this.languageStr;
  }

  /**
   * @param languageStr new value of {@link #getLanguageStr()}.
   */
  public void setLanguageStr(String languageStr) {

    this.languageStr = languageStr;

    setLanguage(LocaleUtils.toLocale(languageStr));
  }

  @Transient
  @Override
  public Locale getLanguage() {

    return this.language;
  }

  @Transient
  @Override
  public void setLanguage(Locale language) {

    this.language = language;

  }

}
