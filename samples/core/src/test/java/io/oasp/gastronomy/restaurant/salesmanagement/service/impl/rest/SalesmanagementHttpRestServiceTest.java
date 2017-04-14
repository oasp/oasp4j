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
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.api.builders.OrderPositionEtoBuilder;
import io.oasp.gastronomy.restaurant.general.common.base.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.salesmanagement.common.api.OrderPosition;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest.SalesmanagementRestService;

/**
 * This class serves as an example of how to perform a subsystem test (e.g., call a *RestService interface). The test
 * database is accessed via HTTP requests to the server running the restaurant application.
 *
 */
@SpringApplicationConfiguration(classes = { SpringBootApp.class, SalesmanagementRestTestConfig.class })
@TestPropertySource(properties = { "flyway.locations=filesystem:src/test/resources/db/tablemanagement" })
public class SalesmanagementHttpRestServiceTest extends AbstractRestServiceTest {

  private final HttpHeaders AUTHENTIFICATED_HEADERS = getAuthentificatedHeaders();

  private SalesmanagementRestService service;

  @Inject
  private SalesmanagementRestServiceTestHelper helper;

  @Inject
  private RestTemplate template;

  /**
   * Provides initialization previous to the creation of the text fixture.
   */
  @Override
  public void doSetUp() {

    super.doSetUp();
    getDbTestHelper().resetDatabase();
    this.service = getRestTestClientBuilder().build(SalesmanagementRestService.class, "waiter");
  }

  /**
   * Provides clean up after tests.
   */
  @Override
  public void doTearDown() {

    this.service = null;
    super.doTearDown();
  }

  /**
   * This test method creates a sample instance of {@link OrderCto} and saves it into the database. Thereafter a HTTP
   * GET request specifying the same id as the previously created {@link OrderCto} object is sent. Finally the body of
   * the response {@link JSONObject} is tested, if it comprises the same attributes as the sample {@link OrderCto}
   * object.
   */
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
  }

  /**
   * At first this method creates a {@link JSONObject} specifying a sample instance of {@link OrderCto} and a sample
   * instance of {@link OrderPositionEto} linked to it by the id/orderId. Thereafter the HTTP Post request is sent.
   * Finally the {@link OrderCto} object and the {@link OrderPositionEto} object are loaded from the database and its
   * attributes are tested for correctness.
   */
  @Test
  public void postOrderWithOrderPosition() {

    // given
    HttpHeaders postRequestHeaders = this.AUTHENTIFICATED_HEADERS;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);

    JSONObject postRequest = new JSONObject();

    JSONObject order = new JSONObject();
    order.put("tableId", SAMPLE_TABLE_ID);
    postRequest.put("order", order);

    JSONArray orderPositions = new JSONArray();
    JSONObject orderPosition = new JSONObject();
    orderPosition.put("offerId", SAMPLE_OFFER_ID);
    orderPosition.put("state", SAMPLE_ORDER_POSITION_STATE);
    orderPosition.put("drinkState", SAMPLE_DRINK_STATE);
    orderPosition.put("comment", SAMPLE_COMMENT);
    orderPositions.put(orderPosition);
    postRequest.put("positions", orderPositions);

    HttpEntity<String> postRequestEntity = new HttpEntity<>(postRequest.toString(), postRequestHeaders);

    // when
    ResponseEntity<String> postResponse =
        this.template.exchange(generateBaseUrl() + "order/", HttpMethod.POST, postRequestEntity, String.class);

    // then
    JSONObject postResponseJson = new JSONObject(postResponse.getBody());
    assertThat(postResponseJson).isNotNull();

    JSONArray responseOrderPositionEtos = postResponseJson.getJSONArray("positions");
    assertThat(responseOrderPositionEtos).isNotNull();
    assertThat(responseOrderPositionEtos.length()).isEqualTo(1);

    int responseOrderId = postResponseJson.getJSONObject("order").getInt("id");
    int responseOrderPositionEtoId = responseOrderPositionEtos.getJSONObject(0).getInt("id");
    assertThat(responseOrderId).isEqualTo(responseOrderPositionEtos.getJSONObject(0).getInt("orderId"));

    OrderEto responseOrderEto = this.service.findOrder(responseOrderId);
    assertThat(responseOrderEto).isNotNull();
    assertThat(responseOrderEto.getId()).isEqualTo(responseOrderId);
    assertThat(responseOrderEto.getTableId()).isEqualTo(SAMPLE_TABLE_ID);

    OrderPositionEto responseOrderPositionEto = this.service.findOrderPosition(responseOrderPositionEtoId);
    assertThat(responseOrderPositionEto).isNotNull();
    assertThat(responseOrderPositionEto.getId()).isEqualTo(responseOrderPositionEtoId);
    assertThat(responseOrderPositionEto.getOrderId()).isEqualTo(responseOrderId);
    assertThat(responseOrderPositionEto.getOfferName()).isEqualTo(SAMPLE_OFFER_NAME);
    assertThat(responseOrderPositionEto.getState()).isEqualTo(SAMPLE_ORDER_POSITION_STATE);
    assertThat(responseOrderPositionEto.getDrinkState()).isEqualTo(SAMPLE_DRINK_STATE);
    assertThat(responseOrderPositionEto.getPrice()).isEqualTo(SAMPLE_PRICE);
    assertThat(responseOrderPositionEto.getComment()).isEqualTo(SAMPLE_COMMENT);
  }

  /**
   * This test method creates a sample instance of {@link OrderCto} and a sample instance of {@link OrderPositionEto}
   * linked to it and saves them to the database. Thereafter a HTTP GET request containing a {@link JSONObject}
   * specifying the same id/orderId as the previously created {@link OrderPositionEto} object is sent. Finally the body
   * of the response {@link JSONObject} is tested, if it comprises the same attributes as the sample
   * {@link OrderPositionEto} object.
   */
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

  /**
   * This test method creates a sample instance of {@link OrderCto} and saves it into the database. In addition some
   * sample instances of {@link OrderPositionEto} are created, linked to the sample {@link OrderCto} object by the
   * id/orderId and saved into the database. Thereafter a HTTP GET request is sent demanding all saved
   * {@link OrderPositionEto} objects in the database. Finally the body of the response {@link JSONObject} is tested
   * concerning the number of loaded orderPositionEtos and the correctness attributes of the sample instances.
   */
  @Test
  public void getAllOrderPositions() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    int oldNumberOfOrderPositions = getNumberOfOrderPositions();
    int numberOfOrderPositionsToSave = NUMBER_OF_SAMPLE_ORDER_POSITIONS;

    OrderPositionEto sampleOrderPositionEto;
    ArrayList<OrderPositionEto> savedOrderPositionEtos = new ArrayList<>();
    for (int i = 0; i < numberOfOrderPositionsToSave; ++i) {
      sampleOrderPositionEto = new OrderPositionEtoBuilder().orderId(responseOrderCto.getOrder().getId())
          .offerId(SAMPLE_OFFER_ID).createNew();
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

    int responseOrderPositionId = 0;
    int countNumberOfSavedOrderPositions = 0;

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

  /**
   * At first the method creates a sample instance of {@link OrderCto} and saves it to the database. Thereafter a
   * {@link JSONObject} specifying a sample instance of {@link OrderPosition} linked to the sample {@link OrderCto}
   * object is created and the corresponding HTTP Post request is sent. Finally the {@link OrderPositionEto} object is
   * loaded from the database and its attributes are tested for correctness.
   */
  @Test
  public void postOrderPosition() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    HttpHeaders postRequestHeaders = this.AUTHENTIFICATED_HEADERS;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    JSONObject postRequest = new JSONObject();

    postRequest.put("orderId", responseOrderCto.getOrder().getId());
    postRequest.put("offerId", SAMPLE_OFFER_ID);
    postRequest.put("state", SAMPLE_ORDER_POSITION_STATE);
    postRequest.put("drinkState", SAMPLE_DRINK_STATE);
    postRequest.put("comment", SAMPLE_COMMENT);

    try {
      postRequest.put("offerName", URLEncoder.encode(SAMPLE_OFFER_NAME, "UTF-8"));
    } catch (JSONException | UnsupportedEncodingException e) {

    }
    postRequest.put("price", SAMPLE_PRICE.getValue());

    HttpEntity<String> postRequestEntity = new HttpEntity<>(postRequest.toString(), postRequestHeaders);

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

  /**
   * This method builds an encoded authentication header and returns it
   */
  private HttpHeaders getAuthentificatedHeaders() {

    String plainCreds = ROLE + ":" + ROLE;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    return headers;
  }

  /**
   * This method creates the base uri of the salesmanagement service. As the port changes in between the invocation of
   * the constructor and the execution of the test method this method is necessary
   */
  private String generateBaseUrl() {

    return BASE_URL_PRAEFIX + this.port + BASE_URL_SUFFIX_1 + BASE_URL_SUFFIX_2;
  }

  /**
   * This test method loads all saved {@link OrderPositionEto} objects from the database, counts the number and returns
   * it.
   *
   * @return number of orderPositions saved in the database
   */
  protected int getNumberOfOrderPositions() {

    int numberOfOrderPositions = 0;
    List<OrderPositionEto> orderPositions = this.service.findOrderPositions(null);
    if (orderPositions != null) {
      numberOfOrderPositions = orderPositions.size();
    }
    return numberOfOrderPositions;
  }
}
