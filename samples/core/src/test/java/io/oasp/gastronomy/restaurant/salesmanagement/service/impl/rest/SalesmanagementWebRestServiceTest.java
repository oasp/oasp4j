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
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.datatype.OrderState;
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

  // reads from test/resources/config/application.properties the variable server.port
  @Value("${local.server.port}")
  private int port;

  @Inject
  private JacksonJsonProvider jacksonJsonProvider;

  private HttpEntity<String> request;

  private HttpHeaders authentificatedHeaders;

  private UriInfo uriInfo;

  private static final String BASE_URL_PRAEFIX = "http://localhost:";

  private static final String BASE_URL_SUFFIX = "/services/rest/salesmanagement/v1/";

  private static final String ROLE = "chief";

  private static final long SAMPLE_ORDER_ID = 1L;

  private static final long SAMPLE_OFFER_ID = 5L;

  private static final String SAMPLE_OFFER_NAME = "Cola";

  private static final OrderState SAMPLE_ORDER_STATE = OrderState.OPEN;

  private static final OrderPositionState SAMPLE_ORDER_POSITION_STATE = OrderPositionState.DELIVERED;

  private static final ProductOrderState SAMPLE_DRINK_STATE = ProductOrderState.DELIVERED;

  private static final String SAMPLE_COMMENT = "";

  private static final double SAMPLE_PRICE = 1.20;

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

    // OrderPositionEto expectedOrderPositionEto = this.service.saveOrderPosition(6, 1, "");

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

    // setup
    long numberOfOrderPositions = 0;
    HttpHeaders postRequestHeaders = this.authentificatedHeaders;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    JSONObject request = new JSONObject();

    request.put("orderId", SAMPLE_ORDER_ID);
    request.put("offerId", SAMPLE_OFFER_ID);
    request.put("offerName", SAMPLE_OFFER_NAME);
    request.put("state", SAMPLE_ORDER_POSITION_STATE);
    request.put("drinkState", SAMPLE_DRINK_STATE);
    request.put("comment", SAMPLE_COMMENT);

    // both operations are redundant as the values of the attributes "offername" and "price" are persisted
    // automatically according to offerId
    request.put("offerName", SAMPLE_OFFER_NAME);
    request.put("price", SAMPLE_PRICE);

    HttpEntity<String> postRequestEntity = new HttpEntity<String>(request.toString(), postRequestHeaders);

    List<OrderPositionEto> orderPositions = this.service.findOrderPositions(this.uriInfo);
    if (orderPositions != null) {
      numberOfOrderPositions = orderPositions.size();
    }

    // execute
    ResponseEntity<String> postResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/", HttpMethod.POST, postRequestEntity, String.class);

    // verify
    OrderPositionEto expectedOrderPositionEto = this.service.findOrderPosition(numberOfOrderPositions + 1);
    assertThat(expectedOrderPositionEto).isNotNull();
    assertThat(expectedOrderPositionEto.getId()).isEqualTo(numberOfOrderPositions + 1);
    assertThat(expectedOrderPositionEto.getOrderId()).isEqualTo(SAMPLE_ORDER_ID);
    assertThat(expectedOrderPositionEto.getOfferName()).isEqualTo(SAMPLE_OFFER_NAME);
    assertThat(expectedOrderPositionEto.getState()).isEqualTo(SAMPLE_ORDER_POSITION_STATE);
    assertThat(expectedOrderPositionEto.getDrinkState()).isEqualTo(SAMPLE_DRINK_STATE);
    assertThat(expectedOrderPositionEto.getPrice().getValue().doubleValue()).isEqualTo(SAMPLE_PRICE);
    assertThat(expectedOrderPositionEto.getComment()).isEqualTo(SAMPLE_COMMENT);
  }

  public HttpHeaders getAuthentificatedHeaders() {

    String plainCreds = ROLE + ":" + ROLE;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    return headers;
  }

  // this function is necessary as the default port changes in between the invocation of the constructor
  // and
  // the execution of the test methods
  public String generateBaseUrl() {

    return BASE_URL_PRAEFIX + this.port + BASE_URL_SUFFIX;
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
