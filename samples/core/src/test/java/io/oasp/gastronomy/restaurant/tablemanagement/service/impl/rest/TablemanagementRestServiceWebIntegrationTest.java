package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
public class TablemanagementRestServiceWebIntegrationTest extends RestaurantWebIntegrationSubsystemTest {

  /**
   * This test method serves as an example of how to use {@link RestraurantWebIntegrationSubsystemTest} in practice.
   */
  @Test
  public void testFindTable() {

    String id = "101";
    TablemanagementRestService service = createRestClient(TablemanagementRestService.class);
    TableEto table = service.getTable(id);
    assertThat(table).isNotNull();
    assertThat(table.getId()).isEqualTo(Long.parseLong(id));
  }

}
