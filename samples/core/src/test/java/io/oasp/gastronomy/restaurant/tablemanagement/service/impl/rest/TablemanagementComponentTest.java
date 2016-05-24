package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import io.oasp.module.test.common.base.ComponentTest;

/**
 * TODO jmolinar This type ...
 *
 * @author jmolinar
 * @since dev
 */
@SpringApplicationConfiguration
@WebAppConfiguration

public class TablemanagementComponentTest extends ComponentTest {
  private static final String LOGIN = "waiter";

  private RestTemplate restTemplate = new TestRestTemplate(LOGIN, LOGIN);

  @Test
  public void test() {

    // this.restTemplate.getForEntity(url, responseType)
  }
}
