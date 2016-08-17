package io.oasp.gastronomy.restaurant.general.common.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.general.common.SecurityTestHelper;
import io.oasp.gastronomy.restaurant.general.configuration.RestaurantTestConfig;
import io.oasp.module.basic.configuration.SpringProfileConstants;
import io.oasp.module.test.common.base.SubsystemTest;
import io.oasp.module.test.common.helper.api.DbTestHelper;

/**
 * Abstract base class for {@link SubsystemTest}s which runs the tests within a local server. <br/>
 * <br/>
 * The local server's port is randomly assigned.
 *
 * @author jmolinar, shuber
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { RestaurantTestConfig.class, SpringBootApp.class })
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
   * The {@code RestaurantTestHelper}.
   */
  @Inject
  private SecurityTestHelper securityTestHelper;

  /**
   * The {@code RestTestClientBuilder}
   */
  @Inject
  private RestTestClientBuilder restTestClientBuilder;

  /**
   * The {@code JacksonJsonProvider}
   */
  @Inject
  private JacksonJsonProvider jacksonJsonProvider;

  @Override
  protected void doSetUp() {

    super.doSetUp();
    this.restTestClientBuilder.setLocalServerPort(this.port);
    this.restTestClientBuilder.setUser(this.user);
    this.restTestClientBuilder.setPassword(this.password);
    this.restTestClientBuilder.setJacksonJsonProvider(this.jacksonJsonProvider);

  }

  @Override
  protected void doTearDown() {

    super.doTearDown();

    // empty implementation which may be overridden by a subclass.
  }

  /**
   * @return the {@link SecurityTestHelper}
   */
  public SecurityTestHelper getSecurityTestHelper() {

    return this.securityTestHelper;
  }

  /**
   * @return the {@link DbTestHelperImpl}
   */
  @Override
  public DbTestHelper getDbTestHelper() {

    return this.dbTestHelper;
  }

  /**
   * Injects {@link DbTestHelper}
   */
  @Inject
  public void setDbTestHelper(DbTestHelper dbTestHelper) {

    this.dbTestHelper = dbTestHelper;
  }

  /**
   * @return the {@link RestTestClientBuilder}
   */
  public RestTestClientBuilder getRestTestClientBuilder() {

    return this.restTestClientBuilder;
  }

}
