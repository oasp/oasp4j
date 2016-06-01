package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
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

// @RunWith(SpringJUnit4ClassRunner.class)

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

public class SalesmanagementWebRestServiceTest extends SubsystemTest {

  private String rootPath;

  private String salesmanagement;

  private String orderPositions;

  // reads from test/resources/config/application.properties the variable server.port
  @Value("${local.server.port}")
  private int port;

  // TODO Inject ...
  private RestTemplate template;

  // TODO just workaraound, as Jonas solution is not yet approved
  @Inject
  private Flyway flyway;

  public SalesmanagementWebRestServiceTest() {
    long sampleOrderId = 1;

    this.template = new RestTemplate();
    this.rootPath = "http://localhost:" + this.port + "/services/rest";
    this.salesmanagement = "/salesmanagement/v1";
    this.orderPositions = "/orderposition/" + Long.toString(sampleOrderId);
  }

  @Before
  public void prepareTest() {

    // this.flyway.clean();
    // this.flyway.migrate();

  }

  @Test
  public void getOrder() {

    // Setup

    // OrderCto sampleOrderCto = createSampleOrderCto(SAMPLE_TABLE_ID);
    // this.service.saveOrder(sampleOrderCto);

    String authentificationResult = authentification();
    System.out.println("-----------------Test---------------------------");
    System.out.println(authentificationResult);
    assertThat(authentificationResult).isNotNull();
    System.out.println("-----------------Test---------------------------");
    String result =
        // this.template.getForObject("http://localhost:" + this.port + "/services/rest", String.class, "chief",
        // "chief");
        this.template.getForObject(
            "http://localhost:" + this.port + "/services/rest" + this.salesmanagement + this.orderPositions,
            String.class, "chief", "chief");
    System.out.println("http://localhost:" + this.port + "/services/rest");
    System.out.println(this.rootPath + this.salesmanagement + this.orderPositions);
    System.out.println(result);
    assertThat(result).isNotNull();

    // RequestEntity request =
    // RequestEntity
    // .get(new URI(
    // "http://localhost:" + this.port + "/services/rest" + this.salesmanagement + this.orderPositions))
    // .accept(MediaType.APPLICATION_JSON).body("");
    // ResponseEntity<String> response = this.template.exchange(request, String.class);

  }

  public String authentification() {

    String plainCreds = "chief:chief";
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = this.template.exchange(
        "http://localhost:" + this.port + "/services/rest" + this.salesmanagement + this.orderPositions, HttpMethod.GET,
        request, String.class);
    String responseText = response.getBody();
    return responseText;
  }

}
