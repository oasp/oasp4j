package io.oasp.gastronomy.restaurant.general.common.base;

import javax.inject.Inject;

import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.general.common.DbTestHelper;
import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.general.common.SecurityTestHelper;
import io.oasp.gastronomy.restaurant.general.service.impl.config.RestaurantTestConfig;
import io.oasp.module.basic.common.api.config.SpringProfileConstants;
import io.oasp.module.test.common.base.SubsystemTest;

/**
 * Abstract base class for {@link SubsystemTest}s which runs the tests within a local server. <br/>
 * <br/>
 * The local server's port is randomly assigned.
 *
 */
@SpringApplicationConfiguration(classes = RestaurantTestConfig.class)
@WebIntegrationTest
@ActiveProfiles(profiles = { SpringProfileConstants.JUNIT })
public abstract class AbstractRestServiceTest extends SubsystemTest {

  /**
   * The port of the web server during the test.
   */
  @Value("${local.server.port}")
  protected int port;

  /**
   * The user name used during the test.
   */
  @Value("${server.rest.test.user}")
  private String user;

  /**
   * The password used during the test.
   */
  @Value("${server.rest.test.password}")
  private String password;

  /**
   * The baseline to be used by {@link Flyway}
   */
  @Value("${server.rest.test.flyway.baseline}")
  private String baseline;

  /**
   * The migration to be used by {@link Flyway}
   */
  @Value("${server.rest.test.flyway.migration}")
  private String migration;

  /**
   * The {@code RestaurantTestHelper}.
   */
  @Inject
  private SecurityTestHelper securityTestHelper;

  @Inject
  private DbTestHelper dbTestHelper;

  /**
   * The {@code RestTestClientBuilder}.
   */
  @Inject
  private RestTestClientBuilder restTestClientBuilder;

  /**
   * The {@code JacksonJsonProvider}
   */
  @Inject
  private JacksonJsonProvider jacksonJsonProvider;

  /**
   * Calls {@link #doSetUp()}.
   */
  @Before
  public final void setUp() {

    doSetUp();
  }

  /**
   * Calls {@link #doTearDown()}.
   */
  @After
  public final void tearDown() {

    doTearDown();
  }

  /**
   * Sets up the test.
   */
  protected void doSetUp() {

    this.restTestClientBuilder.setLocalServerPort(this.port);
    this.restTestClientBuilder.setUser(this.user);
    this.restTestClientBuilder.setPassword(this.password);
    this.restTestClientBuilder.setJacksonJsonProvider(this.jacksonJsonProvider);

    if (this.migration != null && !"".equals(this.migration)) {
      this.dbTestHelper.setMigrationVersion(this.migration);
    }

  }

  /**
   * Cleans up the test.
   */
  protected void doTearDown() {

    // empty implementation which may be overridden by a subclass.
  }

  /**
   * @return the {@link SecurityTestHelper}
   */
  public SecurityTestHelper getSecurityTestHelper() {

    return this.securityTestHelper;
  }

  /**
   * @return the {@link DbTestHelper}
   */
  public DbTestHelper getDbTestHelper() {

    return this.dbTestHelper;
  }

  /**
   * @return the {@link RestTestClientBuilder}
   */
  public RestTestClientBuilder getRestTestClientBuilder() {

    return this.restTestClientBuilder;
  }

}
