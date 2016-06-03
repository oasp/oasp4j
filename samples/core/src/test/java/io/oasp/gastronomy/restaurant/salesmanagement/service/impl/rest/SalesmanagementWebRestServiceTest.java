package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.codec.binary.Base64;
import org.flywaydb.core.Flyway;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderPositionState;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.ProductOrderState;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest.SalesmanagementRestService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApp.class)
// start web application: 0 from random port
@WebIntegrationTest("server.port:0")
// profile: named, logical group of bean definitions
// Bean definition profiles: mechanism for registration of different beans in
// different environments
// @ActiveProfiles(profiles = { OaspProfile.JUNIT_TEST }): Activate Profile "OaspProfile.JUNIT_TEST"
@ActiveProfiles(profiles = { OaspProfile.JUNIT_TEST })

public class SalesmanagementWebRestServiceTest extends SubsystemTest {

  @Inject
  private JacksonJsonProvider jacksonJsonProvider;

  private String baseUrlPraefix = "http://localhost:";

  private String baseUrlSuffix = "/services/rest/salesmanagement/v1/";

  private HttpEntity<String> request;

  private HttpHeaders authentificatedHeaders;

  private UriInfo uriInfo;

  // reads from test/resources/config/application.properties the variable server.port
  @Value("${local.server.port}")
  private int port;

  private SalesmanagementRestService service;

  // TODO Inject ...
  private RestTemplate template;

  // TODO just workaraound, as Jonas solution is not yet approved
  @Inject
  private Flyway flyway;

  public SalesmanagementWebRestServiceTest() {
    long sampleOrderId = 1;
    this.template = new RestTemplate();
    this.authentificatedHeaders = getAuthentificatedHeaders();

  }

  @Before
  public void prepareTest() {

    this.flyway.clean();
    this.flyway.migrate();
    this.service = RestTestClientBuilder.build(SalesmanagementRestService.class, "waiter", "waiter",
        "http://localhost:" + this.port + "/services/rest", this.jacksonJsonProvider);
  }

  // @Test
  public void getOrderPosition() {

    // setup

    HttpEntity<String> getRequest = new HttpEntity<String>(this.authentificatedHeaders);
    ResponseEntity<String> getResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/", HttpMethod.GET, getRequest, String.class);

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
  }

  @Test
  public void postOrderPosition() {

    long sampleOrderID = 1;
    long sampleOfferID = 5;
    String sampleOfferName = "Cola";
    OrderPositionState sampleOrderState = OrderPositionState.ORDERED;
    ProductOrderState sampleDrinkState = ProductOrderState.ORDERED;
    double samplePrice = 1.20;
    String sampleComment = "";

    long numberOfOrderPositions = 0;

    // setup
    HttpHeaders postRequestHeaders = this.authentificatedHeaders;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    JSONObject request = new JSONObject();

    request.put("orderId", sampleOrderID);
    request.put("offerId", sampleOfferID);
    request.put("offerName", sampleOfferName);
    request.put("state", sampleOrderState);
    request.put("drinkState", sampleDrinkState);
    request.put("comment", sampleComment);

    HttpEntity<String> postRequestEntity = new HttpEntity<String>(request.toString(), postRequestHeaders);

    List<OrderPositionEto> orderPositions = this.service.findOrderPositions(this.uriInfo);
    if (orderPositions != null) {
      numberOfOrderPositions = orderPositions.size();
    }

    // execute
    ResponseEntity<String> postResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/", HttpMethod.POST, postRequestEntity, String.class);

    System.out.println("---------------------TEST-------------------");
    System.out.println(postResponse);

    // verify
    OrderPositionEto expectedOrderPositionEto = this.service.findOrderPosition(numberOfOrderPositions + 1);
    assertThat(expectedOrderPositionEto).isNotNull();
    assertThat(expectedOrderPositionEto.getId()).isEqualTo(numberOfOrderPositions + 1);
    assertThat(expectedOrderPositionEto.getOrderId()).isEqualTo(sampleOrderID);
    assertThat(expectedOrderPositionEto.getOfferName()).isEqualTo(sampleOfferName);
    assertThat(expectedOrderPositionEto.getState()).isEqualTo(sampleOrderState);
    assertThat(expectedOrderPositionEto.getDrinkState()).isEqualTo(sampleDrinkState);
    assertThat(expectedOrderPositionEto.getPrice()).isEqualTo(samplePrice);
    assertThat(expectedOrderPositionEto.getComment()).isEqualTo(sampleComment);

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

  // this function is necessary is necessary as the default port changes in between the invocation of the constructor
  // and
  // the execution of the test methods
  public String generateBaseUrl() {

    return this.baseUrlPraefix + this.port + this.baseUrlSuffix;
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
