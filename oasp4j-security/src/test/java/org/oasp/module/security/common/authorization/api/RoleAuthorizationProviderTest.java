package org.oasp.module.security.common.authorization.api;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oasp.module.security.common.exception.InvalidConfigurationException;
import org.oasp.module.security.common.exception.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Sets;

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
   * @throws InvalidConfigurationException test fails
   * @throws PermissionDeniedException test fails
   */
  @Test
  public void testAuthorization_simpleValid() throws IOException, InvalidConfigurationException,
      PermissionDeniedException {

    RoleAuthorizationProvider roleAuthorizationProvider = new RoleAuthorizationProvider(
        this.accessControlSchema_acyclic);

    roleAuthorizationProvider.authorize(Sets.newHashSet("Waiter"), "GET_TABLE");
  }

  /**
   * Tests a simple (direct related) invalid permission
   * 
   * @throws IOException test fails
   * @throws InvalidConfigurationException test fails
   * @throws PermissionDeniedException expected
   */
  @Test(expected = PermissionDeniedException.class)
  public void testAuthorization_simpleInvalid() throws IOException, InvalidConfigurationException,
      PermissionDeniedException {

    RoleAuthorizationProvider roleAuthorizationProvider = new RoleAuthorizationProvider(
        this.accessControlSchema_acyclic);

    roleAuthorizationProvider.authorize(Sets.newHashSet("Waiter"), "REMOVE_TABLE");
  }

  /**
   * Tests a transitive declared valid permission
   * 
   * @throws IOException test fails
   * @throws InvalidConfigurationException test fails
   * @throws PermissionDeniedException test fails
   */
  @Test
  public void testAuthorization_transitiveValid() throws IOException, InvalidConfigurationException,
      PermissionDeniedException {

    RoleAuthorizationProvider roleAuthorizationProvider = new RoleAuthorizationProvider(
        this.accessControlSchema_acyclic);

    roleAuthorizationProvider.authorize(Sets.newHashSet("Chief"), "GET_TABLE");
  }
}
