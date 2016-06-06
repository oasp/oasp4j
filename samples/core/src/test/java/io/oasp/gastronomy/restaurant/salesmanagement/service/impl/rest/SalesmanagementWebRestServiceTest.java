package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
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

  private long numberOfOrderPositions = 0;

  private static final HttpHeaders AUTHENTIFICATED_HEADERS = getAuthentificatedHeaders();

  private UriInfo uriInfo;

  private static final String BASE_URL_PRAEFIX = "http://localhost:";

  private static final String BASE_URL_SUFFIX = "/services/rest/salesmanagement/v1/";

  private static final String ROLE = "chief";

  private static final long SAMPLE_ORDER_ID = 1L;

  private static final long SAMPLE_OFFER_ID = 5L;

  // TODO talk to Jonas, problematic for ü, escapes donnot work \u00fc
  private static final String SAMPLE_OFFER_NAME = "Cola";

  private static final OrderState SAMPLE_ORDER_STATE = OrderState.OPEN;

  private static final OrderPositionState SAMPLE_ORDER_POSITION_STATE = OrderPositionState.DELIVERED;

  private static final ProductOrderState SAMPLE_DRINK_STATE = ProductOrderState.DELIVERED;

  // TODO talk to Jonas, that this does not work for empty strings
  private static final String SAMPLE_COMMENT = "with ice";

  private static final Money SAMPLE_PRICE = new Money(new BigDecimal("1.20"));

  private SalesmanagementRestService service;

  // TODO Inject ...
  private RestTemplate template;

  // TODO just workaraound, as Jonas solution is not yet approved
  @Inject
  private Flyway flyway;

  public SalesmanagementWebRestServiceTest() {
    long sampleOrderId = 1;
    this.template = new RestTemplate();
  }

  @Before
  public void prepareTest() {

    this.flyway.clean();
    this.flyway.migrate();
    // cannot be put into constructor, as port is set after the constructor invocation
    this.service = RestTestClientBuilder.build(SalesmanagementRestService.class, ROLE, ROLE,
        "http://localhost:" + this.port + "/services/rest", this.jacksonJsonProvider);
    this.numberOfOrderPositions = getNumberOfOrderPositions();

  }

  @Test
  public void getOrderPosition() {

    // setup
    HttpEntity<String> getRequest = new HttpEntity<String>(AUTHENTIFICATED_HEADERS);

    OrderPositionEto sampleOrderPositionEto = new OrderPositionEto();
    sampleOrderPositionEto.setOrderId(SAMPLE_ORDER_ID);
    sampleOrderPositionEto.setOfferId(SAMPLE_OFFER_ID);
    sampleOrderPositionEto.setOfferName(SAMPLE_OFFER_NAME);
    sampleOrderPositionEto.setState(SAMPLE_ORDER_POSITION_STATE);
    sampleOrderPositionEto.setDrinkState(SAMPLE_DRINK_STATE);
    sampleOrderPositionEto.setPrice(SAMPLE_PRICE);
    sampleOrderPositionEto.setComment(SAMPLE_COMMENT);

    this.service.saveOrderPosition(sampleOrderPositionEto);

    // execute
    ResponseEntity<String> getResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/" + Long.toString(this.numberOfOrderPositions + 1),
            HttpMethod.GET, getRequest, String.class);

    // validate
    String getResponseJson = getResponse.getBody();
    JSONAssert.assertEquals("{id:" + Long.toString(this.numberOfOrderPositions + 1) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{orderId:" + Long.toString(SAMPLE_ORDER_ID) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{offerId:" + Long.toString(SAMPLE_OFFER_ID) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{offerName:" + SAMPLE_OFFER_NAME + "}", getResponseJson, false);
    JSONAssert.assertEquals("{state:" + SAMPLE_ORDER_POSITION_STATE.toString() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{drinkState:" + SAMPLE_DRINK_STATE.toString() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{price:" + "\"" + SAMPLE_PRICE.getValue() + "\"" + "}", getResponseJson, false);
    JSONAssert.assertEquals("{comment:" + SAMPLE_COMMENT + "}", getResponseJson, false);
  }

  @Test
  public void getOrderPositions() {

    // setup
    HttpEntity<String> getRequest = new HttpEntity<String>(AUTHENTIFICATED_HEADERS);

    long index = 0;

    OrderPositionEto sampleOrderPositionEto = new OrderPositionEto();
    sampleOrderPositionEto.setOrderId(SAMPLE_ORDER_ID);
    sampleOrderPositionEto.setOfferId(SAMPLE_OFFER_ID);
    this.service.saveOrderPosition(sampleOrderPositionEto);

    // execute
    ResponseEntity<String> getResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/", HttpMethod.GET, getRequest, String.class);

    // validate
    String getResponseJson = getResponse.getBody();
    ArrayList<String> jsonObjectArrayList = buildJsonObjectArrayList(getResponseJson);

    for (String jsonObject : jsonObjectArrayList) {
      index++;
      JSONAssert.assertEquals("{id:" + index + "}", jsonObject, false);
    }
    assertThat(index).isEqualTo(getNumberOfOrderPositions());
  }

  @Test
  public void postOrderPosition() {

    // setup
    HttpHeaders postRequestHeaders = AUTHENTIFICATED_HEADERS;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    JSONObject request = new JSONObject();

    request.put("orderId", SAMPLE_ORDER_ID);
    request.put("offerId", SAMPLE_OFFER_ID);
    request.put("state", SAMPLE_ORDER_POSITION_STATE);
    request.put("drinkState", SAMPLE_DRINK_STATE);
    request.put("comment", SAMPLE_COMMENT);

    // both operations are redundant as the values of the attributes "offername" and "price" are persisted
    // automatically according to offerId
    request.put("offerName", SAMPLE_OFFER_NAME);
    request.put("price", SAMPLE_PRICE.getValue());

    HttpEntity<String> postRequestEntity = new HttpEntity<String>(request.toString(), postRequestHeaders);

    // execute
    ResponseEntity<String> postResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/", HttpMethod.POST, postRequestEntity, String.class);

    // verify
    OrderPositionEto expectedOrderPositionEto = this.service.findOrderPosition(this.numberOfOrderPositions + 1);
    assertThat(expectedOrderPositionEto).isNotNull();
    assertThat(expectedOrderPositionEto.getId()).isEqualTo(this.numberOfOrderPositions + 1);
    assertThat(expectedOrderPositionEto.getOrderId()).isEqualTo(SAMPLE_ORDER_ID);
    assertThat(expectedOrderPositionEto.getOfferName()).isEqualTo(SAMPLE_OFFER_NAME);
    assertThat(expectedOrderPositionEto.getState()).isEqualTo(SAMPLE_ORDER_POSITION_STATE);
    assertThat(expectedOrderPositionEto.getDrinkState()).isEqualTo(SAMPLE_DRINK_STATE);
    assertThat(expectedOrderPositionEto.getPrice()).isEqualTo(SAMPLE_PRICE);
    assertThat(expectedOrderPositionEto.getComment()).isEqualTo(SAMPLE_COMMENT);
  }

  private static HttpHeaders getAuthentificatedHeaders() {

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
  private String generateBaseUrl() {

    return BASE_URL_PRAEFIX + this.port + BASE_URL_SUFFIX;
  }

  private long getNumberOfOrderPositions() {

    long numberOfOrderPositions = 0;
    List<OrderPositionEto> orderPositions = this.service.findOrderPositions(this.uriInfo);
    if (orderPositions != null) {
      numberOfOrderPositions = orderPositions.size();
    }
    return numberOfOrderPositions;
  }

  private ArrayList<String> buildJsonObjectArrayList(String getResponseJson) {

    String truncatedGetResponseJson = getResponseJson.replace("[", "").replace("]", "");
    ArrayList<String> arrayList = new ArrayList();
    int index = truncatedGetResponseJson.indexOf("},{");
    String tempStringEnd = truncatedGetResponseJson;
    while (index != -1) {
      String tempStringBegin = tempStringEnd.substring(0, index + 1);
      arrayList.add(tempStringBegin);
      tempStringEnd = tempStringEnd.substring(index + 2);
      index = tempStringEnd.indexOf("},{");
    }
    arrayList.add(tempStringEnd);
    return arrayList;
  }

}
