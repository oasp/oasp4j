package io.oasp.module.security.common.api.accesscontrol;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

/**
 * A {@link AccessControlGroup} represents a collection of {@link AccessControlPermission permissions}. A security
 * administrator assigns a {@link java.security.Principal user} to a {@link AccessControlGroup group} to grant him the
 * {@link AccessControlPermission permissions} of that {@link AccessControlGroup group}.<br/>
 * Please note that a <em>role</em> is a special form of a {@link AccessControlGroup group} that also represents a
 * strategic function. Therefore not every {@link AccessControlGroup group} is a role. Often a user can only have one
 * role or can only act under one role at a time. Unfortunately these terms are often mixed up what is causing
 * confusion.
 *
 * @author hohwille
 */
@XmlRootElement(name = "group")
public class AccessControlGroup extends AccessControl { // implements java.security.acl.Group {

  /** @see #getInherits() */
  @XmlElementWrapper(name = "inherits")
  @XmlIDREF
  @XmlElement(name = "group-ref")
  private List<AccessControlGroup> inherits;

  /** @see #getPermissions() */
  @XmlElementWrapper(name = "permissions")
  @XmlElement(name = "permission")
  private List<AccessControlPermission> permissions;

  /** @see #getType() */
  private String type;

  /**
   * The constructor.
   */
  public AccessControlGroup() {

    super();
  }

  /**
   * The constructor.
   *
   * @param id the {@link #getId() ID}.
   */
  public AccessControlGroup(String id) {

    super(id);
  }

  /**
   * @return the type of this group. E.g. "role", "department", "use-case-group", etc. You can use this for your own
   *         purpose.
   */
  public String getType() {

    if (this.type == null) {
      return "";
    }
    return this.type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {

    this.type = type;
  }

  /**
   * @return inherits
   */
  public List<AccessControlGroup> getInherits() {

    if (this.inherits == null) {
      this.inherits = new ArrayList<>();
    }
    return this.inherits;
  }

  /**
   * @param inherits the inherits to set
   */
  public void setInherits(List<AccessControlGroup> inherits) {

    this.inherits = inherits;
  }

  /**
   * @return the {@link List} of {@link AccessControlPermission}s.
   */
  public List<AccessControlPermission> getPermissions() {

    if (this.permissions == null) {
      this.permissions = new ArrayList<>();
    }
    return this.permissions;
  }

  /**
   * @param permissions the new {@link #getPermissions() permissions}.
   */
  public void setPermissions(List<AccessControlPermission> permissions) {

    this.permissions = permissions;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    // prevent infinity loops or other sick effects
    // result = prime * result + ((this.inherits == null) ? 0 : this.inherits.hashCode());
    result = prime * result + ((this.permissions == null) ? 0 : this.permissions.hashCode());
    result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AccessControlGroup other = (AccessControlGroup) obj;
    // prevent infinity loops or other sick effects...
    // if (!Objects.equal(this.inherits, other.inherits)) {
    // return false;
    // }
    if (!Objects.equal(this.permissions, other.permissions)) {
      return false;
    }
    if (!Objects.equal(this.type, other.type)) {
      return false;
    }
    return true;
  }

}
