package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.flywaydb.core.Flyway;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

  private String salesmanagement;

  private String orderPositions;

  private HttpEntity<String> request;

  private HttpHeaders authentificatedHeaders;

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
    this.salesmanagement = "/salesmanagement/v1";
    this.orderPositions = "/orderposition/" + Long.toString(sampleOrderId);
    this.authentificatedHeaders = getAuthentificatedHeaders();

  }

  @Before
  public void prepareTest() {

    this.flyway.clean();
    this.flyway.migrate();
  }

  @Test
  public void getOrder() {

    // ResponseEntity<String> responseNew = this.template.exchange(
    // "http://localhost:" + this.port + "/services/rest" + this.salesmanagement + this.orderPositions,
    // HttpMethod.POST, this.request, String.class);
    // System.out.println(responseNew);
    System.out.println("-----------------Test1-----------------");
    // setup

    HttpEntity<String> getRequest = new HttpEntity<String>(this.authentificatedHeaders);

    ResponseEntity<String> getResponse = this.template.exchange(
        "http://localhost:" + this.port + "/services/rest" + this.salesmanagement + this.orderPositions, HttpMethod.GET,
        getRequest, String.class);

    // execute
    String responseJson = getResponse.getBody();
    System.out.println(responseJson);
    System.out.println("-----------------Test2-----------------");
    assertThat(getResponse).isNotNull();
    JSONAssert.assertEquals("{id:1}", responseJson, false);
    JSONAssert.assertEquals("{modificationCounter:1}", responseJson, false);
    JSONAssert.assertEquals("{orderId:1}", responseJson, false);
    JSONAssert.assertEquals("{offerId:1}", responseJson, false);
    JSONAssert.assertEquals("{offerName:Schnitzel-Menü}", responseJson, false);

    System.out.println("-----------------Test2-----------------");
    HttpHeaders postRequestHeaders = this.authentificatedHeaders;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    JSONObject request = new JSONObject();

    request.put("id", 6);
    request.put("modificationCounter", 1);
    // request.put("revision", Null);
    request.put("orderId", 1);
    request.put("offerId", 1);
    request.put("offerName", "Schnitzel-Menü");
    request.put("state", "DELIVERED");
    request.put("drinkState", "DELIVERED");
    request.put("price", "6.99");
    request.put("comment", "mit Ketschup");
    // {"id":1,"modificationCounter":1,"revision":null,"orderId":1,"cookId":null,"offerId":1,"offerName":"Schnitzel-Menü","state":"DELIVERED","drinkState":"DELIVERED","price":"6.99","comment":"mit
    // Ketschup"}
    String request2 =
        "{id:6,modificationCounter:1,revision:null,orderId:1,cookId:null, offerId:1,\"offerName\":\"Schnitzel-Menü\",\"state\":\"DELIVERED\",\"drinkState\":\"DELIVERED\",\"price\":\"6.99\",\"comment\":\"mit Ketschup\"}";
    HttpEntity<String> postRequestEntity = new HttpEntity<String>(request2, postRequestHeaders);
    System.out.println("-----------------request-----------------");
    System.out.println(request2);
    ResponseEntity<String> putResponse =
        // this.template.exchange("http://localhost:" + this.port + "/services/rest" + this.salesmanagement
        // + "/orderposition/" + Long.toString(6), HttpMethod.POST, postRequestEntity, String.class);
        this.template.exchange(
            "http://localhost:" + this.port + "/services/rest" + this.salesmanagement + "/orderposition",
            HttpMethod.POST, postRequestEntity, String.class);

    System.out.println("-----------------putResponse-----------------");
    System.out.println(putResponse);

    // RequestEntity request =
    // RequestEntity
    // .get(new URI(
    // "http://localhost:" + this.port + "/services/rest" + this.salesmanagement + this.orderPositions))
    // .accept(MediaType.APPLICATION_JSON).body("");
    // ResponseEntity<String> response = this.template.exchange(request, String.class);

  }

  public HttpHeaders getAuthentificatedHeaders() {

    String plainCreds = "chief:chief";
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    return headers;
  }

  // public HttpEntity<String> getAuthentificatedRequest() {
  //
  // String plainCreds = "chief:chief";
  // byte[] plainCredsBytes = plainCreds.getBytes();
  // byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
  // String base64Creds = new String(base64CredsBytes);
  //
  // HttpHeaders headers = new HttpHeaders();
  // headers.add("Authorization", "Basic " + base64Creds);
  //
  // HttpEntity<String> request = new HttpEntity<String>(headers);
  // return request;
  // }

}
