package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.service.api.rest.TablemanagementRestService;
import io.oasp.module.basic.configuration.OaspProfile;
import io.oasp.module.test.common.base.SubsystemTest;

/**
 * This class serves as an example of how to perform a subsystem test (e.g., call a *RestService interface).
 *
 * @author geazzi
 */

// Test
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApp.class)
@WebIntegrationTest("server.port:0")
@ActiveProfiles(profiles = { OaspProfile.JUNIT_TEST })
public class TablemanagementRestServiceTest extends SubsystemTest {

  @Value("${local.server.port}")
  private int port;

  @Inject
  private JacksonJsonProvider jacksonJsonProvider;

  @Test
  public void testFindTable() {

    TablemanagementRestService service = RestTestClientBuilder.build(TablemanagementRestService.class, "waiter",
        "waiter", "http://localhost:" + this.port + "/services/rest", this.jacksonJsonProvider);
    TableEto table = service.getTable("101");
    assertThat(table).isNotNull();
  }

}
