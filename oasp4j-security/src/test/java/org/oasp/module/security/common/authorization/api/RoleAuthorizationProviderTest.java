package org.oasp.module.security.common.authorization.api;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.stub;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.oasp.module.security.common.exception.InvalidConfigurationException;
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
  private static final String LOGIN_CHIEF = "Chief";

  /**
   * Login as Role Waiter.
   */
  private static final String LOGIN_WAITER = "Waiter";

  @Value("classpath:/config/accessControlSchema_cyclic.xml")
  private Resource accessControlSchema_cyclic;

  @Value("classpath:/config/accessControlSchema_acyclic.xml")
  private Resource accessControlSchema_acyclic;

  @Value("classpath:/config/accessControlSchema_corrupted.xml")
  private Resource accessControlSchema_corrupted;

  @Mock
  private RolesProvider rolesProvider;

  /**
   * Initialize Mocks
   */
  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);
  }

  private void mockRolesProvider() {

    stub(this.rolesProvider.hasOneOf(anyString(), anyList())).toAnswer(new Answer<Boolean>() {
      @Override
      public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {

        String userToken = (String) invocationOnMock.getArguments()[0];
        List<String> roles = (List<String>) invocationOnMock.getArguments()[1];
        return roles.contains(userToken);
      }
    });
  }

  /**
   * Tests, whether the {@link RoleAuthorizationProvider} detects include loops over 3 edges
   *
   * @throws IOException test fails
   * @throws InvalidConfigurationException expected
   */
  @Test(expected = InvalidConfigurationException.class)
  public void testCyclicConfiguration() throws IOException, InvalidConfigurationException {

    new RoleAuthorizationProvider(this.accessControlSchema_cyclic);
  }

  /**
   * Tests, whether the {@link RoleAuthorizationProvider} detects a corrupted file
   *
   * @throws IOException test fails
   * @throws InvalidConfigurationException expected
   */
  @Test(expected = InvalidConfigurationException.class)
  public void testCorruptedConfiguration() throws IOException, InvalidConfigurationException {

    new RoleAuthorizationProvider(this.accessControlSchema_corrupted);
  }

  /**
   * Tests a simple (direct related) valid permission
   *
   * @throws IOException test fails
   * @throws SecurityException test fails
   */
  @Test
  public void testAuthorization_simpleValid() throws IOException, SecurityException {

    RoleAuthorizationProvider roleAuthorizationProvider =
        new RoleAuthorizationProvider(this.accessControlSchema_acyclic);
    roleAuthorizationProvider.setRolesProvider(this.rolesProvider);
    mockRolesProvider();

    roleAuthorizationProvider.authorize(LOGIN_WAITER, "GET_TABLE");
  }

  /**
   * Tests a simple (direct related) invalid permission
   *
   * @throws IOException test fails
   * @throws SecurityException expected
   */
  @Test(expected = SecurityException.class)
  public void testAuthorization_simpleInvalid() throws IOException, SecurityException {

    RoleAuthorizationProvider roleAuthorizationProvider =
        new RoleAuthorizationProvider(this.accessControlSchema_acyclic);
    roleAuthorizationProvider.setRolesProvider(this.rolesProvider);
    mockRolesProvider();

    roleAuthorizationProvider.authorize(LOGIN_WAITER, "REMOVE_TABLE");
  }

  /**
   * Tests a simple (direct related) invalid permission
   *
   * @throws IOException test fails
   * @throws SecurityException expected
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAuthorizationRequestedPermission_simpleInvalid() throws IOException, SecurityException {

    RoleAuthorizationProvider roleAuthorizationProvider =
        new RoleAuthorizationProvider(this.accessControlSchema_acyclic);
    roleAuthorizationProvider.setRolesProvider(this.rolesProvider);
    mockRolesProvider();

    roleAuthorizationProvider.authorize(LOGIN_WAITER, "STASH_TABLE");
  }

  /**
   * Tests a transitive declared valid permission
   *
   * @throws IOException test fails
   * @throws SecurityException test fails
   */
  @Test
  public void testAuthorization_transitiveValid() throws IOException, SecurityException {

    RoleAuthorizationProvider roleAuthorizationProvider =
        new RoleAuthorizationProvider(this.accessControlSchema_acyclic);
    roleAuthorizationProvider.setRolesProvider(this.rolesProvider);
    mockRolesProvider();

    roleAuthorizationProvider.authorize(LOGIN_CHIEF, "GET_TABLE");
  }
}
