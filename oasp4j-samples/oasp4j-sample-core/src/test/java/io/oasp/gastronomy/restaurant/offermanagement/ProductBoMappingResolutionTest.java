package io.oasp.gastronomy.restaurant.offermanagement;

import static org.junit.Assert.assertEquals;
import io.oasp.gastronomy.restaurant.general.common.AbstractSpringIntegrationTest;
import io.oasp.gastronomy.restaurant.offermanagement.service.impl.rest.OffermanagementRestServiceImpl;
import io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceImpl;
import io.oasp.gastronomy.restaurant.staffmanagement.service.impl.rest.StaffmanagementRestServiceImpl;
import io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to.TableEto;
import io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest.TablemanagementRestServiceImpl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.local.LocalConduit;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author agreul
 */
@Ignore("local service needs to be running first for testing method")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/config/app/service/beans-test-service-rest.xml" })
public class ProductBoMappingResolutionTest extends AbstractSpringIntegrationTest {

  private static final String REST_SERVICE_URL = "local://rest";

  // @Inject
  // private List<RestService> restServices;

  private Server server;

  @SuppressWarnings("javadoc")
  @PostConstruct
  public void initialize() throws Exception {

    startServer();
  }

  private void startServer() throws Exception {

    // List<ResourceProvider> providerList = new ArrayList<>(this.restServices.size());
    // for (RestService service : this.restServices) {
    // SingletonResourceProvider singletonResourceProvider = new SingletonResourceProvider(service, true);
    // providerList.add(singletonResourceProvider);
    // }
    JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
    sf.setResourceClasses(TablemanagementRestServiceImpl.class, OffermanagementRestServiceImpl.class,
        SalesmanagementRestServiceImpl.class, StaffmanagementRestServiceImpl.class);

    // add custom providers if any
    // sf.setProvider(JacksonJaxbJsonProvider.class);
    // sf.setResourceProviders(providerList);
    sf.setAddress(REST_SERVICE_URL);
    // BindingFactoryManager manager =
    // this.sf.getBus().getExtension(BindingFactoryManager.class);
    // JAXRSBindingFactory factory = new JAXRSBindingFactory();
    // factory.setBus(this.sf.getBus());
    // manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID,
    // factory);
    this.server = sf.create();
  }

  @SuppressWarnings("javadoc")
  @PreDestroy
  public void destroy() throws Exception {

    this.server.stop();
    this.server.destroy();
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testGetTableViaPipe() {

    WebClient client = WebClient.create(REST_SERVICE_URL);
    WebClient.getConfig(client).getRequestContext().put(LocalConduit.DIRECT_DISPATCH, Boolean.TRUE);
    client.accept("application/json");
    client.path("/tablemanagment/table/1");
    TableEto table = client.get(TableEto.class);
    assertEquals(Long.valueOf(1), table.getId());
  }
}
