package io.oasp.gastronomy.restaurant.tablemanagement.logic.impl;

import io.oasp.gastronomy.restaurant.general.common.AbstractSpringIntegrationTest;
import io.oasp.gastronomy.restaurant.general.common.RestServiceTestProvider;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.local.LocalConduit;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

/**
 * 
 * @author agreul
 */
@Ignore("Currently not working/incomplete")
@ContextConfiguration({ "classpath:/config/app/service/beans-test-service-rest.xml" })
public class TableManagementTest extends AbstractSpringIntegrationTest {

  @Inject
  private RestServiceTestProvider restServicetestProvider;

  @Test
  public void testGetTableWithWebClientDirectDispatch() {

    WebClient client = WebClient.create(this.restServicetestProvider.getEndpointAddress());
    WebClient.getConfig(client).getRequestContext().put(LocalConduit.DIRECT_DISPATCH, Boolean.TRUE);

    client.accept("application/json");
    client.path("table/1");
    Response response = client.get();
    assertEquals(response.getMediaType(), (MediaType.APPLICATION_JSON));
    // assertEquals(response.getEntity(), TableBo.class);
  }
}
