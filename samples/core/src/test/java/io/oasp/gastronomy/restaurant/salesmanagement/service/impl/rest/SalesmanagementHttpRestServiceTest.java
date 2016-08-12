package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.oasp.gastronomy.restaurant.general.common.SampleCreator;
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
 * @author shuber
 */

public class SalesmanagementHttpRestServiceTest extends AbstractRestServiceTest {

  private final HttpHeaders AUTHENTIFICATED_HEADERS = getAuthentificatedHeaders();

  private static Logger LOG = LoggerFactory.getLogger(SalesmanagementHttpRestServiceTest.class);

  private SalesmanagementRestService service;

  protected static final String ROLE = "chief";

  @Inject
  private RestTemplate template;

  @Override
  public void doSetUp() {

    super.doSetUp();
    this.service = getRestTestClientBuilder().build(SalesmanagementRestService.class);
  }

  @Override
  public void doTearDown() {

    super.doTearDown();
    this.service = null;
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
    OrderCto sampleOrderCto = SampleCreator.createSampleOrderCto();
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
    JSONAssert.assertEquals("{tableId:" + Long.toString(SampleCreator.SAMPLE_TABLE_ID) + "}", getResponseJson, false);
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
    order.put("tableId", SampleCreator.SAMPLE_TABLE_ID);
    postRequest.put("order", order);

    JSONArray orderPositions = new JSONArray();
    JSONObject orderPosition = new JSONObject();
    orderPosition.put("offerId", SampleCreator.SAMPLE_OFFER_ID);
    orderPosition.put("state", SampleCreator.SAMPLE_ORDER_POSITION_STATE);
    orderPosition.put("drinkState", SampleCreator.SAMPLE_DRINK_STATE);
    orderPosition.put("comment", SampleCreator.NEW_ORDER_COMMENT);
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
    assertThat(responseOrderEto.getTableId()).isEqualTo(SampleCreator.SAMPLE_TABLE_ID);

    OrderPositionEto responseOrderPositionEto = this.service.findOrderPosition(responseOrderPositionEtoId);
    assertThat(responseOrderPositionEto).isNotNull();
    assertThat(responseOrderPositionEto.getId()).isEqualTo(responseOrderPositionEtoId);
    assertThat(responseOrderPositionEto.getOrderId()).isEqualTo(responseOrderId);
    assertThat(responseOrderPositionEto.getOfferName()).isEqualTo(SampleCreator.SAMPLE_OFFER_NAME);
    assertThat(responseOrderPositionEto.getState()).isEqualTo(SampleCreator.SAMPLE_ORDER_POSITION_STATE);
    assertThat(responseOrderPositionEto.getDrinkState()).isEqualTo(SampleCreator.SAMPLE_DRINK_STATE);
    assertThat(responseOrderPositionEto.getPrice()).isEqualTo(SampleCreator.SAMPLE_ORDERPOSITION_PRICE_AS_MONEY);
    assertThat(responseOrderPositionEto.getComment()).isEqualTo(SampleCreator.NEW_ORDER_COMMENT);

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
    OrderCto sampleOrderCto = SampleCreator.createSampleOrderCto();
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    OrderPositionEto sampleOrderPositionEto =
        SampleCreator.createSampleOrderPositionEto(responseOrderCto.getOrder().getId());
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
    JSONAssert.assertEquals("{offerId:" + Long.toString(SampleCreator.SAMPLE_OFFER_ID) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{offerName:" + SampleCreator.SAMPLE_OFFER_NAME + "}", getResponseJson, false);
    JSONAssert.assertEquals("{state:" + SampleCreator.SAMPLE_ORDER_POSITION_STATE.toString() + "}", getResponseJson,
        false);
    JSONAssert.assertEquals("{drinkState:" + SampleCreator.SAMPLE_DRINK_STATE.toString() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{price:" + "\"" + SampleCreator.SAMPLE_ORDERPOSITION_PRICE + "\"" + "}", getResponseJson,
        false);
    JSONAssert.assertEquals("{comment:" + SampleCreator.NEW_ORDER_COMMENT + "}", getResponseJson, false);
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
    OrderCto sampleOrderCto = SampleCreator.createSampleOrderCto();
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    int oldNumberOfOrderPositions = getNumberOfOrderPositions();
    int numberOfOrderPositionsToSave = SampleCreator.NUMBER_OF_NEW_ORDER_POSITIONS;

    OrderPositionEto sampleOrderPositionEto;
    ArrayList<OrderPositionEto> savedOrderPositionEtos = new ArrayList();
    for (int i = 0; i < numberOfOrderPositionsToSave; ++i) {
      sampleOrderPositionEto = new OrderPositionEto();
      sampleOrderPositionEto.setOrderId(responseOrderCto.getOrder().getId());
      sampleOrderPositionEto.setOfferId(SampleCreator.SAMPLE_OFFER_ID);
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
          JSONAssert.assertEquals("{offerId:" + Long.toString(SampleCreator.SAMPLE_OFFER_ID) + "}",
              responseOrderPositions.getJSONObject(i), false);
          JSONAssert.assertEquals("{offerName:" + SampleCreator.SAMPLE_OFFER_NAME + "}",
              responseOrderPositions.getJSONObject(i), false);
          JSONAssert.assertEquals("{price:" + "\"" + SampleCreator.SAMPLE_ORDERPOSITION_PRICE + "\"" + "}",
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
    OrderCto sampleOrderCto = SampleCreator.createSampleOrderCto();
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    HttpHeaders postRequestHeaders = this.AUTHENTIFICATED_HEADERS;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    JSONObject postRequest = new JSONObject();

    postRequest.put("orderId", responseOrderCto.getOrder().getId());
    postRequest.put("offerId", SampleCreator.SAMPLE_OFFER_ID);
    postRequest.put("state", SampleCreator.SAMPLE_ORDER_POSITION_STATE);
    postRequest.put("drinkState", SampleCreator.SAMPLE_DRINK_STATE);
    postRequest.put("comment", SampleCreator.NEW_ORDER_COMMENT);

    try {
      postRequest.put("offerName", URLEncoder.encode(SampleCreator.SAMPLE_OFFER_NAME, "UTF-8"));
    } catch (JSONException | UnsupportedEncodingException e) {

    }
    postRequest.put("price", SampleCreator.SAMPLE_ORDERPOSITION_PRICE);

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
    assertThat(responseOrderPositionEto.getOfferId()).isEqualTo(SampleCreator.SAMPLE_OFFER_ID);
    assertThat(responseOrderPositionEto.getOfferName()).isEqualTo(SampleCreator.SAMPLE_OFFER_NAME);
    assertThat(responseOrderPositionEto.getState()).isEqualTo(SampleCreator.SAMPLE_ORDER_POSITION_STATE);
    assertThat(responseOrderPositionEto.getDrinkState()).isEqualTo(SampleCreator.SAMPLE_DRINK_STATE);
    assertThat(responseOrderPositionEto.getPrice()).isEqualTo(SampleCreator.SAMPLE_ORDERPOSITION_PRICE_AS_MONEY);
    assertThat(responseOrderPositionEto.getComment()).isEqualTo(SampleCreator.NEW_ORDER_COMMENT);

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

    return "http://localhost:" + this.port + "/services/rest/salesmanagement/v1/";
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
