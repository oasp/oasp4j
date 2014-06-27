package org.oasp.module.security.common.authorization.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.oasp.module.security.common.exception.InvalidConfigurationException;
import org.oasp.module.security.common.exception.PermissionDeniedException;
import org.oasp.security.Include;
import org.oasp.security.Permission;
import org.oasp.security.Role;
import org.oasp.security.Security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * The {@link RoleAuthorizationProvider} provides a simple authorization managment of roles and permissions.
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
   * Mapping from permission id to list of roles, having this permission
   */
  private Map<String, Set<String>> flattenPermissions = new HashMap<>();

  /**
   * Creates a new {@link RoleAuthorizationProvider} with the given {@link Resource} as configuration.
   * 
   * @param accessControlSchema configuration file
   * @throws IOException if the configuration file could not be read.
   * @throws InvalidConfigurationException if the configuration file does not match the security schema definition
   */
  public RoleAuthorizationProvider(Resource accessControlSchema) throws IOException, InvalidConfigurationException {

    InputStream in = accessControlSchema.getInputStream();
    try {
      JAXBContext context = JAXBContext.newInstance(Security.class);
      Unmarshaller unmarschaller = context.createUnmarshaller();
      Security configuration = (Security) unmarschaller.unmarshal(in);
      calculatePermissionToRoleMapping(configuration);
    } catch (JAXBException e) {
      // TODO (mbrunnli) message handling
      LOG.error("Could not parse file. Configuration does not match the schema definition.");
      throw new InvalidConfigurationException(
          "Could not parse file. Configuration does not match the schema definition.", e);
    }
  }

  /**
   * Calculates the permission to all parent roles mapping of the given security configuration
   * 
   * @param configuration {@link Security} configuration instance
   * @throws InvalidConfigurationException if a include cycle has been detected between the roles
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
   * Collects all parents (including transitive ones) for the given {@link Permission}. Therefore it needs the child to
   * parent mapping of all roles.
   * 
   * @param childToParentsMapping mapping of all child roles to their parents
   * @param role first role to calculate all parents for
   * @return all parents for the given permission
   * @throws InvalidConfigurationException if a include cycle has been detected between the roles
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
        // TODO (mbrunnli) message handling
        LOG.error("The security configuration contains an include cycle of roles. Detected at role '{}'",
            currentParent);
        throw new InvalidConfigurationException(
            "The security configuration contains an include cycle of roles. Detected at role '" + currentParent
                + "'");
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
   * Validates whether the given user roles are sufficient to match the given target permission.
   * 
   * @param userRoles roles of the user
   * @param targetPermission permission to be granted
   * @throws PermissionDeniedException if none of the user's roles contains the target permission
   */
  public void authorize(Set<String> userRoles, String targetPermission) throws PermissionDeniedException {

    Set<String> possibleRoles = this.flattenPermissions.get(targetPermission);
    if (possibleRoles == null) {
      // TODO (mbrunnli) message handling
      LOG.error("Permission denied due to requested permission is not declared in the security configuration!");
      throw new IllegalArgumentException(
          "Permission denied due to requested permission is not declared in the security configuration!");
    }
    HashSet<String> _userRoles = new HashSet<>(userRoles);
    _userRoles.retainAll(possibleRoles);
    if (_userRoles.isEmpty()) {
      // TODO (mbrunnli) message handling
      LOG.error("Permission denied: the given user roles do not include the target permission.");
      throw new PermissionDeniedException(
          "Permission denied: the given user roles do not include the target permission.");
    }
  }
}
