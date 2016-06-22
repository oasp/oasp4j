package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.BASE_URL_PRAEFIX;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.BASE_URL_SUFFIX_1;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.BASE_URL_SUFFIX_2;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.NUMBER_OF_SAMPLE_ORDER_POSITIONS;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.ROLE;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_COMMENT;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_DRINK_STATE;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_OFFER_ID;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_OFFER_NAME;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_ORDER_POSITION_STATE;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_PRICE;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.SAMPLE_TABLE_ID;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.base.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest.SalesmanagementRestService;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { SpringBootApp.class, SalesmanagementRestTestConfiguration.class })
@TestPropertySource(properties = { "flyway.locations=filesystem:src/test/resources/db/tablemanagement" })

public class SalesmanagementWebRestServiceTest extends AbstractRestServiceTest {

  private final HttpHeaders AUTHENTIFICATED_HEADERS = getAuthentificatedHeaders();

  private static Logger LOG = LoggerFactory.getLogger(SalesmanagementWebRestServiceTest.class);

  private SalesmanagementRestService service;

  @Inject
  private SalesmanagementRestServiceTestHelper helper;

  // TODO Inject ...
  private RestTemplate template;

  public SalesmanagementWebRestServiceTest() {

    this.template = new RestTemplate();
  }

  @Before
  public void init() {

    getDbTestHelper().resetDatabase();
    this.service = getRestTestClientBuilder().build(SalesmanagementRestService.class);
  }

  @After
  public void clean() {

    this.service = null;
  }

  @Test
  public void getOrder() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    HttpEntity<String> getRequest = new HttpEntity<>(this.AUTHENTIFICATED_HEADERS);

    // when
    ResponseEntity<String> getResponse = this.template.exchange(
        generateBaseUrl() + "order/" + responseOrderCto.getOrder().getId(), HttpMethod.GET, getRequest, String.class);

    // then
    assertThat(getResponse).isNotNull();
    String getResponseJson = getResponse.getBody();
    assertThat(getResponseJson).isNotNull();
    JSONAssert.assertEquals("{id:" + responseOrderCto.getOrder().getId() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{tableId:" + Long.toString(SAMPLE_TABLE_ID) + "}", getResponseJson, false);
    // TODO ask Jonas if this is too much? If not adjustments of other methods necessary
    JSONAssert.assertEquals("{tableId:" + Long.toString(responseOrderCto.getOrder().getTableId()) + "}",
        getResponseJson, false);

  }

  // TODO implement corresponding to getAllOrderPositions
  @Test
  public void getAllOrders() {

  }

  // @Test
  // public void getOrderWithPositions() {
  //
  // // given
  // HttpEntity<String> getRequest = new HttpEntity<>(this.AUTHENTIFICATED_HEADERS);
  // OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
  // this.service.saveOrder(sampleOrderCto);
  //
  // // when
  // ResponseEntity<String> getResponse = this.template.exchange(
  // // TODO change id like this in other requests
  // generateBaseUrl() + "order/" + sampleOrderCto.getOrder().getId(), HttpMethod.GET, getRequest, String.class);
  //
  // // then
  // assertThat(getResponse).isNotNull();
  // String getResponseJson = getResponse.getBody();
  // JSONAssert.assertEquals("{id:" + sampleOrderCto.getOrder().getId() + "}", getResponseJson, false);
  // JSONAssert.assertEquals("{tableId:" + Long.toString(SAMPLE_TABLE_ID) + "}", getResponseJson, false);
  // }

  @Test
  public void postOrderWithOrderPosition() {

    // given
    HttpHeaders postRequestHeaders = this.AUTHENTIFICATED_HEADERS;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);

    // TODO ask Jonas regarding naming (postResponseJson)
    JSONObject request = new JSONObject();

    JSONObject order = new JSONObject();
    order.put("tableId", SAMPLE_TABLE_ID);
    request.put("order", order);

    JSONArray orderPositions = new JSONArray();
    JSONObject orderPosition = new JSONObject();
    orderPosition.put("offerId", SAMPLE_OFFER_ID);
    orderPosition.put("state", SAMPLE_ORDER_POSITION_STATE);
    orderPosition.put("drinkState", SAMPLE_DRINK_STATE);
    orderPosition.put("comment", SAMPLE_COMMENT);
    orderPositions.put(orderPosition);
    request.put("positions", orderPositions);

    HttpEntity<String> postRequestEntity = new HttpEntity<>(request.toString(), postRequestHeaders);

    // when
    ResponseEntity<String> postResponse =
        this.template.exchange(generateBaseUrl() + "order/", HttpMethod.POST, postRequestEntity, String.class);

    // then
    JSONObject postResponseJson = new JSONObject(postResponse.getBody());
    assertThat(postResponseJson).isNotNull();

    JSONArray responseOrderPositions = postResponseJson.getJSONArray("positions");
    assertThat(responseOrderPositions).isNotNull();
    assertThat(responseOrderPositions.length()).isEqualTo(1);

    long responseOrderId = postResponseJson.getJSONObject("order").getInt("id");
    long responseOrderPositionId = responseOrderPositions.getJSONObject(0).getInt("id");
    assertThat(responseOrderId).isEqualTo(responseOrderPositions.getJSONObject(0).getInt("orderId"));

    OrderEto responseOrderEto = this.service.findOrder(responseOrderId);
    assertThat(responseOrderEto).isNotNull();
    assertThat(responseOrderEto.getId()).isEqualTo(responseOrderId);
    assertThat(responseOrderEto.getTableId()).isEqualTo(SAMPLE_TABLE_ID);

    OrderPositionEto responseOrderPositionEto = this.service.findOrderPosition(responseOrderPositionId);
    assertThat(responseOrderPositionEto).isNotNull();
    assertThat(responseOrderPositionEto.getId()).isEqualTo(responseOrderPositionId);
    assertThat(responseOrderPositionEto.getOrderId()).isEqualTo(responseOrderId);
    assertThat(responseOrderPositionEto.getOfferName()).isEqualTo(SAMPLE_OFFER_NAME);
    assertThat(responseOrderPositionEto.getState()).isEqualTo(SAMPLE_ORDER_POSITION_STATE);
    assertThat(responseOrderPositionEto.getDrinkState()).isEqualTo(SAMPLE_DRINK_STATE);
    assertThat(responseOrderPositionEto.getPrice()).isEqualTo(SAMPLE_PRICE);
    assertThat(responseOrderPositionEto.getComment()).isEqualTo(SAMPLE_COMMENT);
  }

  @Test
  public void getOrderPosition() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    OrderPositionEto sampleOrderPositionEto =
        this.helper.createSampleOrderPositionEto(responseOrderCto.getOrder().getId());
    OrderPositionEto responseOrderPositionEto = this.service.saveOrderPosition(sampleOrderPositionEto);
    assertThat(responseOrderPositionEto).isNotNull();
    assertThat(responseOrderPositionEto.getOrderId()).isEqualTo(responseOrderCto.getOrder().getId());

    HttpEntity<String> getRequest = new HttpEntity<>(this.AUTHENTIFICATED_HEADERS);

    // when
    ResponseEntity<String> getResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/" + Long.toString(responseOrderPositionEto.getId()),
            HttpMethod.GET, getRequest, String.class);

    // then
    assertThat(getResponse).isNotNull();
    String getResponseJson = getResponse.getBody();
    assertThat(getResponseJson).isNotNull();

    JSONAssert.assertEquals("{id:" + Long.toString(responseOrderPositionEto.getId()) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{orderId:" + Long.toString(responseOrderCto.getOrder().getId()) + "}", getResponseJson,
        false);
    JSONAssert.assertEquals("{offerId:" + Long.toString(SAMPLE_OFFER_ID) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{offerName:" + SAMPLE_OFFER_NAME + "}", getResponseJson, false);
    JSONAssert.assertEquals("{state:" + SAMPLE_ORDER_POSITION_STATE.toString() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{drinkState:" + SAMPLE_DRINK_STATE.toString() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{price:" + "\"" + SAMPLE_PRICE.getValue() + "\"" + "}", getResponseJson, false);
    JSONAssert.assertEquals("{comment:" + SAMPLE_COMMENT + "}", getResponseJson, false);
  }

  @Test
  public void getAllOrderPositions() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    int oldNumberOfOrderPositions = getNumberOfOrderPositions();
    int numberOfOrderPositionsToSave = NUMBER_OF_SAMPLE_ORDER_POSITIONS;

    OrderPositionEto sampleOrderPositionEto;
    ArrayList<OrderPositionEto> savedOrderPositionEtos = new ArrayList();
    for (int i = 0; i < numberOfOrderPositionsToSave; ++i) {
      sampleOrderPositionEto = new OrderPositionEto();
      sampleOrderPositionEto.setOrderId(responseOrderCto.getOrder().getId());
      sampleOrderPositionEto.setOfferId(SAMPLE_OFFER_ID);
      savedOrderPositionEtos.add(this.service.saveOrderPosition(sampleOrderPositionEto));
    }

    int newNumberOfOrderPositions = getNumberOfOrderPositions();

    HttpEntity<String> getRequest = new HttpEntity<>(this.AUTHENTIFICATED_HEADERS);

    // when
    ResponseEntity<String> getResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/", HttpMethod.GET, getRequest, String.class);

    // then
    assertThat(getResponse).isNotNull();

    JSONArray responseOrderPositions = new JSONArray(getResponse.getBody());
    assertThat(responseOrderPositions).isNotNull();

    assertThat(oldNumberOfOrderPositions + numberOfOrderPositionsToSave).isEqualTo(newNumberOfOrderPositions);
    assertThat(responseOrderPositions.length()).isEqualTo(newNumberOfOrderPositions);

    long responseOrderPositionId = 0;
    long countNumberOfSavedOrderPositions = 0;

    for (int i = 0; i < responseOrderPositions.length(); ++i) {
      responseOrderPositionId = responseOrderPositions.getJSONObject(i).getInt("id");
      for (OrderPositionEto orderPositionEto : savedOrderPositionEtos) {
        if (responseOrderPositionId == orderPositionEto.getId()) {

          JSONAssert.assertEquals("{orderId:" + Long.toString(orderPositionEto.getOrderId()) + "}",
              responseOrderPositions.getJSONObject(i), false);
          JSONAssert.assertEquals("{offerId:" + Long.toString(SAMPLE_OFFER_ID) + "}",
              responseOrderPositions.getJSONObject(i), false);
          JSONAssert.assertEquals("{offerName:" + SAMPLE_OFFER_NAME + "}", responseOrderPositions.getJSONObject(i),
              false);
          JSONAssert.assertEquals("{price:" + "\"" + SAMPLE_PRICE.getValue() + "\"" + "}",
              responseOrderPositions.getJSONObject(i).toString(), false);

          countNumberOfSavedOrderPositions++;
        }
      }
    }

    assertThat(countNumberOfSavedOrderPositions).isEqualTo(numberOfOrderPositionsToSave);
  }

  @Test
  public void putOrderPosition() {

  }

  @Test
  public void postOrderPosition() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    HttpHeaders postRequestHeaders = this.AUTHENTIFICATED_HEADERS;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    JSONObject request = new JSONObject();

    request.put("orderId", responseOrderCto.getOrder().getId());
    request.put("offerId", SAMPLE_OFFER_ID);
    request.put("state", SAMPLE_ORDER_POSITION_STATE);
    request.put("drinkState", SAMPLE_DRINK_STATE);
    request.put("comment", SAMPLE_COMMENT);

    try {
      request.put("offerName", URLEncoder.encode(SAMPLE_OFFER_NAME, "UTF-8"));
    } catch (JSONException | UnsupportedEncodingException e) {

    }
    request.put("price", SAMPLE_PRICE.getValue());

    HttpEntity<String> postRequestEntity = new HttpEntity<>(request.toString(), postRequestHeaders);

    // when
    ResponseEntity<String> postResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/", HttpMethod.POST, postRequestEntity, String.class);

    // then

    assertThat(postResponse).isNotNull();
    JSONObject postResponseJson = new JSONObject(postResponse.getBody());
    assertThat(postResponseJson).isNotNull();

    OrderPositionEto responseOrderPositionEto = this.service.findOrderPosition(postResponseJson.getInt("id"));
    assertThat(responseOrderPositionEto).isNotNull();
    assertThat(responseOrderPositionEto.getId()).isEqualTo(postResponseJson.getInt("id"));
    assertThat(responseOrderPositionEto.getOrderId()).isEqualTo(responseOrderCto.getOrder().getId());
    assertThat(responseOrderPositionEto.getOfferId()).isEqualTo(SAMPLE_OFFER_ID);
    assertThat(responseOrderPositionEto.getOfferName()).isEqualTo(SAMPLE_OFFER_NAME);
    assertThat(responseOrderPositionEto.getState()).isEqualTo(SAMPLE_ORDER_POSITION_STATE);
    assertThat(responseOrderPositionEto.getDrinkState()).isEqualTo(SAMPLE_DRINK_STATE);
    assertThat(responseOrderPositionEto.getPrice()).isEqualTo(SAMPLE_PRICE);
    assertThat(responseOrderPositionEto.getComment()).isEqualTo(SAMPLE_COMMENT);
  }

  private HttpHeaders getAuthentificatedHeaders() {

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

    return BASE_URL_PRAEFIX + this.port + BASE_URL_SUFFIX_1 + BASE_URL_SUFFIX_2;
  }

  protected int getNumberOfOrderPositions() {

    int numberOfOrderPositions = 0;
    List<OrderPositionEto> orderPositions = this.service.findOrderPositions(null);
    if (orderPositions != null) {
      numberOfOrderPositions = orderPositions.size();
    }
    return numberOfOrderPositions;
  }
}
