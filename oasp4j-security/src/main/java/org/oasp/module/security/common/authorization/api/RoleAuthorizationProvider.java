package org.oasp.module.security.common.authorization.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.oasp.module.security.common.exception.InvalidConfigurationException;
import org.oasp.security.Include;
import org.oasp.security.Permission;
import org.oasp.security.Role;
import org.oasp.security.Security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * The {@link RoleAuthorizationProvider} provides a simple authorization
 * management of roles and permissions.
 * 
 * @author mbrunnli
 * @version $Id:$
 */
public class RoleAuthorizationProvider {

  /**
   * Logger instance
   */
  private static final Logger LOG = LoggerFactory.getLogger(RoleAuthorizationProvider.class);

  /**
   * Role provider to check a user's roles
   */
  private RolesProvider rolesProvider;

  /**
   * Mapping from permission id to list of roles, having this permission
   */
  private Map<String, Set<String>> flattenPermissions = new HashMap<>();

  /**
   * Creates a new {@link RoleAuthorizationProvider} with the given
   * {@link Resource} as configuration.
   * 
   * @param accessControlSchema configuration file
   * @throws IOException if the configuration file could not be read.
   * @throws InvalidConfigurationException if the configuration file does not
   *         match the security schema definition
   */
  public RoleAuthorizationProvider(Resource accessControlSchema) throws IOException {

    InputStream in = accessControlSchema.getInputStream();
    try {
      JAXBContext context = JAXBContext.newInstance(Security.class);
      Unmarshaller unmarschaller = context.createUnmarshaller();
      Security configuration = (Security) unmarschaller.unmarshal(in);
      calculatePermissionToRoleMapping(configuration);
    } catch (JAXBException e) {
      LOG.error("Could not parse file. Configuration does not match the schema definition.");
      throw new InvalidConfigurationException(
          "Could not parse file. Configuration does not match the schema definition.", e);
    }
  }

  /**
   * Validates whether the user's roles are sufficient to match the given target
   * permission.
   * 
   * @param userToken user token
   * @param targetPermission permission to be granted
   * @throws SecurityException if none of the user's roles contains the target
   *         permission
   */
  public void authorize(Object userToken, String targetPermission) throws SecurityException {

    authorize(userToken, Arrays.asList(targetPermission));
  }

  /**
   * Validates whether the user's roles are sufficient to match at least one of
   * the given target permissions.
   * 
   * @param userToken user token
   * @param targetPermissions permissions to be granted
   * @throws SecurityException if none of the user's roles contains the target
   *         permission
   */
  public void authorize(Object userToken, List<String> targetPermissions) throws SecurityException {

    if (targetPermissions.isEmpty())
      return;

    Set<String> possibleRoles = new HashSet<>();
    for (String targetPermission : targetPermissions) {
      if (this.flattenPermissions.get(targetPermission) != null) {
        possibleRoles.addAll(this.flattenPermissions.get(targetPermission));
      } else {
        LOG.error(
            "Permission denied due to requested permission is not declared in the security configuration! Permission "
                + "'{}' not declared in accessControlSchema.", targetPermission);
        throw new IllegalArgumentException(
            "Permission denied due to requested permission is not declared in the security configuration! Permission "
                + "'" + targetPermission + "' not declared in accessControlSchema.");
      }
    }

    if (!this.rolesProvider.hasOneOf(userToken, new LinkedList<>(possibleRoles))) {
      LOG.error("Permission denied: the given user roles do not include the target permission.");
      throw new SecurityException("Permission denied: the given user roles do not include the target permission.");
    }
  }

  /**
   * Calculates the permission to all parent roles mapping of the given security
   * configuration
   * 
   * @param configuration {@link Security} configuration instance
   * @throws InvalidConfigurationException if a include cycle has been detected
   *         between the roles
   */
  private void calculatePermissionToRoleMapping(Security configuration) throws InvalidConfigurationException {

    // create roles -> roles include the key role Mapping
    Map<String, Set<String>> includes = new HashMap<>();
    for (Role parentRole : configuration.getRoles().getRole()) {
      for (Include include : parentRole.getInclude()) {
        String includedRole = include.getRef();
        if (!includes.containsKey(includedRole)) {
          includes.put(includedRole, new HashSet<String>());
        }
        // add child -> parent Mapping of roles
        includes.get(includedRole).add(parentRole.getName());
      }
    }

    // create permission -> roles which have the permission Mapping
    this.flattenPermissions = new HashMap<>();
    for (Role role : configuration.getRoles().getRole()) {
      for (Permission rolePermission : role.getPermission()) {
        if (!this.flattenPermissions.containsKey(rolePermission.getName())) {
          this.flattenPermissions.put(rolePermission.getName(), new HashSet<String>());
        }

        // add permission parent role and all transitive parent roles
        Set<String> visitedRoles = calculateTransitiveClosure(includes, role);
        this.flattenPermissions.get(rolePermission.getName()).addAll(visitedRoles);
      }
    }
  }

  /**
   * Collects all parents (including transitive ones) for the given
   * {@link Permission}. Therefore it needs the child to parent mapping of all
   * roles.
   * 
   * @param childToParentsMapping mapping of all child roles to their parents
   * @param role first role to calculate all parents for
   * @return all parents for the given permission
   * @throws InvalidConfigurationException if a include cycle has been detected
   *         between the roles
   */
  private Set<String> calculateTransitiveClosure(Map<String, Set<String>> childToParentsMapping, Role role)
      throws InvalidConfigurationException {

    Set<String> visitedRoles = new HashSet<>();
    LinkedList<String> parentWorklist = new LinkedList<>();
    parentWorklist.add(role.getName());
    while (!parentWorklist.isEmpty()) {
      String currentParent = parentWorklist.pop();
      // include loop detection and handling
      if (visitedRoles.contains(currentParent)) {
        LOG.error("The security configuration contains an include cycle of roles. Detected at role '{}'", currentParent);
        throw new InvalidConfigurationException(
            "The security configuration contains an include cycle of roles. Detected at role '" + currentParent + "'");
      }

      Set<String> parentRoles = childToParentsMapping.get(currentParent);
      if (parentRoles != null) {
        parentWorklist.addAll(parentRoles);
      }
      visitedRoles.add(currentParent);
    }
    return visitedRoles;
  }

  /**
   * Sets the field 'rolesProvider'
   * 
   * @param rolesProvider new value for rolesProvider
   */
  @Inject
  public void setRolesProvider(RolesProvider rolesProvider) {

    this.rolesProvider = rolesProvider;
  }
}
