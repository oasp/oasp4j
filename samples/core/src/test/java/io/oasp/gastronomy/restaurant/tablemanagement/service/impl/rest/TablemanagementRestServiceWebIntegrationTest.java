package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.base.RestaurantWebIntegrationSubsystemTest;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.service.api.rest.TablemanagementRestService;

/**
 * This class serves as an example of how to perform a subsystem test (e.g., call a *RestService interface).
 *
 * @author geazzi, jmolinar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApp.class)
@TestPropertySource(properties = "flyway.locations=filesystem:src/test/resources/db/tablemanagement")
public class TablemanagementRestServiceWebIntegrationTest extends RestaurantWebIntegrationSubsystemTest {

  private static Logger LOG = LoggerFactory.getLogger(TablemanagementRestServiceWebIntegrationTest.class);

  /**
   * Provides initialization prvious to the creation of the text fixture.
   */
  @Before
  public void init() {

    getRestaurantTestHelper().migrate();
  }

  /**
   * This test method serves as an example of how to use {@link RestaurantWebIntegrationSubsystemTest} in practice.
   */
  @Test
  public void testFindTable() {

    String id = "102";
    TablemanagementRestService service = getRestTestClientBuilder().build(TablemanagementRestService.class);
    TableEto table = service.getTable(id);
    assertThat(table).isNotNull();
    assertThat(table.getId()).isEqualTo(Long.parseLong(id));

  }

}
