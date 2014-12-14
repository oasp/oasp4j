package io.oasp.gastronomy.restaurant.general.common;

import io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest.TablemanagementRestServiceImpl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * This is the TestProvider for RestService testing based on a minimal local service. The correspondent client is
 * registered within the ProductBoMappingResolutionTest.
 *
 * @author agreul
 */
public class RestServiceTestProvider {

  private final static String ENDPOINT_ADDRESS = "local://rest/";

  /**
   * @return base url of endpointAddress
   */
  public String getEndpointAddress() {

    return ENDPOINT_ADDRESS;
  }

  // @Inject
  // private List<RestService> restServices;

  private Server server;

  /**
   * The constructor.
   */
  public RestServiceTestProvider() {

    super();
  }

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
    sf.setResourceClasses(TablemanagementRestServiceImpl.class);

    // add custom providers if any
    sf.setProvider(JacksonJsonProvider.class);

    // sf.setResourceProviders(providerList);
    sf.setAddress(ENDPOINT_ADDRESS);

    this.server = sf.create();
  }

  @SuppressWarnings("javadoc")
  @PreDestroy
  public void destroy() throws Exception {

    this.server.stop();
    this.server.destroy();
  }

}
