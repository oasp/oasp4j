package io.oasp.gastronomy.restaurant.general.common.base;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.general.common.RestraurantTestHelper;
import io.oasp.gastronomy.restaurant.general.common.api.RestService;
import io.oasp.gastronomy.restaurant.general.configuration.RestaurantTestConfig;
import io.oasp.module.basic.configuration.SpringProfileConstants;
import io.oasp.module.test.common.base.SubsystemTest;

/**
 * Abstract base class for {@link SubsystemTest}s which runs the tests within a local server. <br/>
 * <br/>
 * The local server's port is randomly assigned.
 *
 * @author jmolinar
 */
@SpringApplicationConfiguration(classes = RestaurantTestConfig.class)
@WebIntegrationTest
@ActiveProfiles(profiles = { SpringProfileConstants.JUNIT })
public class RestaurantWebIntegrationSubsystemTest extends SubsystemTest {

  /**
   *
   */
  private static final String LOGIN = "waiter";

  /**
   * The port of the web server during the test.
   */
  @Value("${local.server.port}")
  protected int port;

  @Inject
  private RestraurantTestHelper restaurantTestHelper;

  @Inject
  private RestTestClientBuilder restTestClientBuilder;

  @Inject
  private JacksonJsonProvider jacksonJsonProvider;

  @Before
  public final void setUp() {

    doSetUp();
  }

  @After
  public final void testDown() {

    doTearDown();
  }

  protected void doSetUp() {

  }

  protected void doTearDown() {

    // TODO implement
  }

  protected boolean isRestDatabase() {

    return true;
  }

  public RestraurantTestHelper getRestaurantTestHelper() {

    return this.restaurantTestHelper;
  }

  protected <T extends RestService> T createRestClient(Class<T> serviceInterface) {

    this.restTestClientBuilder.setLocalServerPort(this.port);
    this.restTestClientBuilder.setJacksonJsonProvider(this.jacksonJsonProvider);
    return this.restTestClientBuilder.build(serviceInterface, LOGIN, LOGIN);
  }
}
