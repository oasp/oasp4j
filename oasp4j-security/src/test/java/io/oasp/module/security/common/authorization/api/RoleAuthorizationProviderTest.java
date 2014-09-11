package io.oasp.module.security.common.authorization.api;

import io.oasp.module.security.common.exception.InvalidConfigurationException;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the {@link RoleAuthorizationProvider} implementation
 *
 * @author mbrunnli
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/config/spring/spring_dummy.xml" })
public class RoleAuthorizationProviderTest {

  /**
   * Login as Role Chief.
   */
  private static final String ROLE_CHIEF = "Chief";

  /**
   * Login as Role Waiter.
   */
  private static final String ROLE_WAITER = "Waiter";

  @Value("classpath:/config/accessControlSchema_cyclic.xml")
  private Resource accessControlSchema_cyclic;

  @Value("classpath:/config/accessControlSchema_acyclic.xml")
  private Resource accessControlSchema_acyclic;

  @Value("classpath:/config/accessControlSchema_corrupted.xml")
  private Resource accessControlSchema_corrupted;

  /**
   * Tests, whether the {@link RoleAuthorizationProvider} detects include loops over 3 edges
   *
   * @throws IOException test fails
   * @throws InvalidConfigurationException expected
   */
  @Test(expected = InvalidConfigurationException.class)
  public void testCyclicConfiguration() throws IOException, InvalidConfigurationException {

    new RoleAuthorizationProvider<DummyUser>(this.accessControlSchema_cyclic);
  }

  /**
   * Tests, whether the {@link RoleAuthorizationProvider} detects a corrupted file
   *
   * @throws IOException test fails
   * @throws InvalidConfigurationException expected
   */
  @Test(expected = InvalidConfigurationException.class)
  public void testCorruptedConfiguration() throws IOException, InvalidConfigurationException {

    new RoleAuthorizationProvider<DummyUser>(this.accessControlSchema_corrupted);
  }

  private RoleAuthorizationProvider<DummyUser> createAuthorizationProvider() throws IOException {

    RoleAuthorizationProvider<DummyUser> roleAuthorizationProvider =
        new RoleAuthorizationProvider<>(this.accessControlSchema_acyclic);
    roleAuthorizationProvider.setRolesProvider(new DummyRolesProvider());
    return roleAuthorizationProvider;
  }

  /**
   * Tests a simple (direct related) valid permission
   *
   * @throws IOException test fails
   * @throws SecurityException test fails
   */
  @Test
  public void testAuthorization_simpleValid() throws IOException, SecurityException {

    createAuthorizationProvider().authorize(new DummyUser(ROLE_WAITER), "GET_TABLE");
  }

  /**
   * Tests a simple (direct related) invalid permission
   *
   * @throws IOException test fails
   * @throws SecurityException expected
   */
  @Test(expected = SecurityException.class)
  public void testAuthorization_simpleInvalid() throws IOException, SecurityException {

    createAuthorizationProvider().authorize(new DummyUser(ROLE_WAITER), "REMOVE_TABLE");
  }

  /**
   * Tests a simple (direct related) invalid permission
   *
   * @throws IOException test fails
   * @throws SecurityException expected
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAuthorizationRequestedPermission_simpleInvalid() throws IOException, SecurityException {

    createAuthorizationProvider().authorize(new DummyUser(ROLE_WAITER), "STASH_TABLE");
  }

  /**
   * Tests a transitive declared valid permission
   *
   * @throws IOException test fails
   * @throws SecurityException test fails
   */
  @Test
  public void testAuthorization_transitiveValid() throws IOException, SecurityException {

    createAuthorizationProvider().authorize(new DummyUser(ROLE_CHIEF), "GET_TABLE");
  }

  private static class DummyUser implements Principal {

    private final String role;

    public DummyUser(String role) {

      this.role = role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {

      return this.role;
    }

    /**
     * @return role
     */
    public String getRole() {

      return this.role;
    }
  }

  private static class DummyRolesProvider implements RolesProvider<DummyUser> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasOneOf(DummyUser userToken, List<String> roles) {

      return roles.contains(userToken.getRole());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(DummyUser userToken, String role) {

      return userToken.getRole().equals(role);
    }

  }
}
