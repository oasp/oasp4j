package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.module.basic.configuration.OaspProfile;
import io.oasp.module.test.common.base.SubsystemTest;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */

@RunWith(SpringJUnit4ClassRunner.class)

// @SpringApplicationConfiguration used to configure the ApplicationContext used in the test
// Starts application as SpringBootApp
@SpringApplicationConfiguration(classes = SpringBootApp.class)
// start web application: 0 from random port
@WebIntegrationTest("server.port:0")
// profile: named, logical group of bean definitions
// Bean definition profiles: mechanism for registration of different beans in
// different environments
// @ActiveProfiles(profiles = { OaspProfile.JUNIT_TEST }): Activate Profile "OaspProfile.JUNIT_TEST"
@ActiveProfiles(profiles = { OaspProfile.JUNIT_TEST })

public class TablemanagementWebRestServiceTest extends SubsystemTest {

  // reads from test/resources/config/application.properties the variable server.port
  @Value("${local.server.port}")
  private int port;

  // TODO Inject ...
  private RestTemplate template;

  public TablemanagementWebRestServiceTest() {
    this.template = new RestTemplate();
  }

  @Test
  public void testHttpConnection() {

    String result = this.template.getForObject("http://localhost:" + this.port + "/services/rest", String.class,
        "waiter", "waiter");

    System.out.println(result);
    assertThat(result).isNotNull();

    /*
     * RequestEntity request = RequestEntity.get(new URI("http://example.com/foo")).accept(MediaType.APPLICATION_JSON)
     * .body(mapper.writeValueAsBytes(userJson)); ResponseEntity<String> response = this.template.exchange(request,
     * String.class);
     */

  }

}
