package org.oasp.module.security.common.authorization.api;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.oasp.module.security.common.exception.InvalidConfigurationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

/**
 * Tests the {@link RoleAuthorizationProvider} implementation
 * 
 * @author mbrunnli
 * @version $Id:$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/config/spring/spring_dummy.xml" })
public class RoleAuthorizationProviderTest {

  @Value("classpath:/config/accessControlSchema_cyclic.xml")
  private Resource accessControlSchema_cyclic;

  @Value("classpath:/config/accessControlSchema_acyclic.xml")
  private Resource accessControlSchema_acyclic;

  @Mock
  private RolesProvider rolesProvider;

  /**
   * Initialize Mocks
   */
  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);
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
   * Tests a simple (direct related) valid permission
   * 
   * @throws IOException test fails
   * @throws SecurityException test fails
   */
  @Test
  public void testAuthorization_simpleValid() throws IOException, SecurityException {

    RoleAuthorizationProvider roleAuthorizationProvider = new RoleAuthorizationProvider(
        this.accessControlSchema_acyclic);
    roleAuthorizationProvider.setRolesProvider(this.rolesProvider);
    when(this.rolesProvider.hasOneOf("Waiter", Lists.newArrayList("Waiter", "Chief"))).thenReturn(true);

    roleAuthorizationProvider.authorize("Waiter", "GET_TABLE");
  }

  /**
   * Tests a simple (direct related) invalid permission
   * 
   * @throws IOException test fails
   * @throws SecurityException expected
   */
  @Test(expected = SecurityException.class)
  public void testAuthorization_simpleInvalid() throws IOException, SecurityException {

    RoleAuthorizationProvider roleAuthorizationProvider = new RoleAuthorizationProvider(
        this.accessControlSchema_acyclic);
    roleAuthorizationProvider.setRolesProvider(this.rolesProvider);
    when(this.rolesProvider.hasOneOf("Waiter", Lists.newArrayList("Waiter", "Chief"))).thenReturn(false);

    roleAuthorizationProvider.authorize("Waiter", "REMOVE_TABLE");
  }

  /**
   * Tests a transitive declared valid permission
   * 
   * @throws IOException test fails
   * @throws SecurityException test fails
   */
  @Test
  public void testAuthorization_transitiveValid() throws IOException, SecurityException {

    RoleAuthorizationProvider roleAuthorizationProvider = new RoleAuthorizationProvider(
        this.accessControlSchema_acyclic);
    roleAuthorizationProvider.setRolesProvider(this.rolesProvider);
    when(this.rolesProvider.hasOneOf("Chief", Lists.newArrayList("Waiter", "Chief"))).thenReturn(true);

    roleAuthorizationProvider.authorize("Chief", "GET_TABLE");
  }
}
