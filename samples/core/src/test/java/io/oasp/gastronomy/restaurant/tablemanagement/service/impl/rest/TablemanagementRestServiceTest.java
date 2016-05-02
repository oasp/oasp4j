package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import java.util.Arrays;
import java.util.HashMap;

import javax.inject.Inject;

import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.impl.TablemanagementImpl;
import io.oasp.gastronomy.restaurant.tablemanagement.service.api.rest.TablemanagementRestService;
import io.oasp.module.test.common.base.SubsystemTest;

/**
 * TODO geazzi This type ...
 *
 * @author geazzi
 * @since dev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApp.class)
@WebIntegrationTest("server.port:0")

public class TablemanagementRestServiceTest extends SubsystemTest {

  @Value("${local.server.port}")
  private int port;

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(TablemanagementImpl.class);

  @Inject
  private JacksonJsonProvider jacksonJsonProvider;

  @Test
  public void testFindTable() {

    TablemanagementRestService service = createService("waiter", "waiter");
    TableEto table = service.getTable("101");
    assertThat(table).isNotNull();
  }

  /**
   * @return
   */
  private TablemanagementRestService createService(String user, String password) {

    JAXRSClientFactoryBean factoryBean = new JAXRSClientFactoryBean();
    factoryBean.setAddress("http://localhost:" + this.port + "/services/rest");
    factoryBean.setHeaders(new HashMap<String, String>());
    // example for basic auth
    String payload = user + ":" + password;
    String authorizationHeader = "Basic " + Base64Utility.encode(payload.getBytes());
    factoryBean.getHeaders().put("Authorization", Arrays.asList(authorizationHeader));
    factoryBean.setProviders(Arrays.asList(this.jacksonJsonProvider));

    // JAXRSClientFactoryBean factoryBean = createJaxRsClientFactory(user, password);
    factoryBean.setServiceClass(TablemanagementRestService.class);
    return factoryBean.create(TablemanagementRestService.class);
  }

}
