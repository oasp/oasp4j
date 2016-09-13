package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import static io.oasp.gastronomy.restaurant.common.builders.OrderPositionEtoBuilder.COMMENT_ORDERPOSITION;
import static io.oasp.gastronomy.restaurant.common.builders.OrderPositionEtoBuilder.NAME_OFFER_SCHNITZELMENUE;
import static io.oasp.gastronomy.restaurant.common.builders.OrderPositionEtoBuilder.PRICE_OFFER_SCHNITZELMENUE_AS_MONEY;
import static io.oasp.gastronomy.restaurant.common.builders.OrderPositionEtoBuilder.STATE_ORDERPOSITION;
import static io.oasp.gastronomy.restaurant.common.builders.OrderPositionEtoBuilder.STATE_PRODUCT_DRINK;
import static io.oasp.gastronomy.restaurant.offermanagement.common.OffermanagementTestDataConstants.ID_OFFER_SCHNITZELMENUE;
import static io.oasp.gastronomy.restaurant.tablemanagement.common.TablemanagementTestDataConstants.ID_TABLE;

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
import io.oasp.gastronomy.restaurant.common.builders.OrderCtoBuilder;
import io.oasp.gastronomy.restaurant.common.builders.OrderPositionEtoBuilder;
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
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { SpringBootApp.class, SalesmanagementRestTestConfiguration.class })
@TestPropertySource(properties = { "flyway.locations=filesystem:src/test/resources/db/tablemanagement" })

public class SalesmanagementHttpRestServiceTest extends AbstractRestServiceTest {

  private final HttpHeaders AUTHENTIFICATED_HEADERS = getAuthentificatedHeaders();

  private static Logger LOG = LoggerFactory.getLogger(SalesmanagementHttpRestServiceTest.class);

  private SalesmanagementRestService service;

  protected static final String ROLE = "chief";

  @Inject
  private RestTemplate template;

  /**
   * Provides initialization previous to the creation of the text fixture.
   */
  @Before
  public void init() {

    getDbTestHelper().resetDatabase();
    this.service = getRestTestClientBuilder().build(SalesmanagementRestService.class);
  }

  /**
   * Provides clean up after tests.
   */
  @After
  public void clean() {

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
    OrderCto sampleOrderCto = new OrderCtoBuilder().createNew();
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
    JSONAssert.assertEquals("{tableId:" + Long.toString(ID_TABLE) + "}", getResponseJson, false);
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
    order.put("tableId", ID_TABLE);
    postRequest.put("order", order);

    JSONArray orderPositions = new JSONArray();
    JSONObject orderPosition = new JSONObject();
    orderPosition.put("offerId", ID_OFFER_SCHNITZELMENUE);
    orderPosition.put("state", STATE_ORDERPOSITION);
    orderPosition.put("drinkState", STATE_PRODUCT_DRINK);
    orderPosition.put("comment", COMMENT_ORDERPOSITION);
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
    assertThat(responseOrderEto.getTableId()).isEqualTo(ID_TABLE);

    OrderPositionEto responseOrderPositionEto = this.service.findOrderPosition(responseOrderPositionEtoId);
    assertThat(responseOrderPositionEto).isNotNull();
    assertThat(responseOrderPositionEto.getId()).isEqualTo(responseOrderPositionEtoId);
    assertThat(responseOrderPositionEto.getOrderId()).isEqualTo(responseOrderId);
    assertThat(responseOrderPositionEto.getOfferName()).isEqualTo(NAME_OFFER_SCHNITZELMENUE);
    assertThat(responseOrderPositionEto.getState()).isEqualTo(STATE_ORDERPOSITION);
    assertThat(responseOrderPositionEto.getDrinkState()).isEqualTo(STATE_PRODUCT_DRINK);
    assertThat(responseOrderPositionEto.getPrice()).isEqualTo(PRICE_OFFER_SCHNITZELMENUE_AS_MONEY);
    assertThat(responseOrderPositionEto.getComment()).isEqualTo(COMMENT_ORDERPOSITION);
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
    OrderCto sampleOrderCto = new OrderCtoBuilder().createNew();
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    OrderPositionEto sampleOrderPositionEto =
        new OrderPositionEtoBuilder().orderId(responseOrderCto.getOrder().getId()).createNew();
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
    JSONAssert.assertEquals("{offerId:" + Long.toString(ID_OFFER_SCHNITZELMENUE) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{offerName:" + NAME_OFFER_SCHNITZELMENUE + "}", getResponseJson, false);
    JSONAssert.assertEquals("{state:" + STATE_ORDERPOSITION.toString() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{drinkState:" + STATE_PRODUCT_DRINK.toString() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{price:" + "\"" + PRICE_OFFER_SCHNITZELMENUE_AS_MONEY.getValue() + "\"" + "}",
        getResponseJson, false);
    JSONAssert.assertEquals("{comment:" + COMMENT_ORDERPOSITION + "}", getResponseJson, false);
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
    OrderCto sampleOrderCto = new OrderCtoBuilder().createNew();
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    int oldNumberOfOrderPositions = getNumberOfOrderPositions();
    int numberOfSampleOrderpositions = 2;

    OrderPositionEto sampleOrderPositionEto;
    ArrayList<OrderPositionEto> savedOrderPositionEtos = new ArrayList();
    for (int i = 0; i < numberOfSampleOrderpositions; ++i) {
      sampleOrderPositionEto = new OrderPositionEtoBuilder().orderId(responseOrderCto.getOrder().getId()).createNew();
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

    assertThat(oldNumberOfOrderPositions + numberOfSampleOrderpositions).isEqualTo(newNumberOfOrderPositions);
    assertThat(responseOrderPositions.length()).isEqualTo(newNumberOfOrderPositions);

    int responseOrderPositionId = 0;
    int countNumberOfSavedOrderPositions = 0;

    for (int i = 0; i < responseOrderPositions.length(); ++i) {
      responseOrderPositionId = responseOrderPositions.getJSONObject(i).getInt("id");
      for (OrderPositionEto orderPositionEto : savedOrderPositionEtos) {
        if (responseOrderPositionId == orderPositionEto.getId()) {

          JSONAssert.assertEquals("{orderId:" + Long.toString(orderPositionEto.getOrderId()) + "}",
              responseOrderPositions.getJSONObject(i), false);
          JSONAssert.assertEquals("{offerId:" + Long.toString(ID_OFFER_SCHNITZELMENUE) + "}",
              responseOrderPositions.getJSONObject(i), false);
          JSONAssert.assertEquals("{offerName:" + NAME_OFFER_SCHNITZELMENUE + "}",
              responseOrderPositions.getJSONObject(i), false);
          JSONAssert.assertEquals("{price:" + "\"" + PRICE_OFFER_SCHNITZELMENUE_AS_MONEY.getValue() + "\"" + "}",
              responseOrderPositions.getJSONObject(i).toString(), false);

          countNumberOfSavedOrderPositions++;
        }
      }
    }

    assertThat(countNumberOfSavedOrderPositions).isEqualTo(numberOfSampleOrderpositions);
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
    OrderCto sampleOrderCto = new OrderCtoBuilder().createNew();
    OrderCto responseOrderCto = this.service.saveOrder(sampleOrderCto);
    assertThat(responseOrderCto).isNotNull();

    HttpHeaders postRequestHeaders = this.AUTHENTIFICATED_HEADERS;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    JSONObject postRequest = new JSONObject();

    postRequest.put("orderId", responseOrderCto.getOrder().getId());
    postRequest.put("offerId", ID_OFFER_SCHNITZELMENUE);
    postRequest.put("state", STATE_ORDERPOSITION);
    postRequest.put("drinkState", STATE_PRODUCT_DRINK);
    postRequest.put("comment", COMMENT_ORDERPOSITION);

    try {
      postRequest.put("offerName", URLEncoder.encode(NAME_OFFER_SCHNITZELMENUE, "UTF-8"));
    } catch (JSONException | UnsupportedEncodingException e) {

    }
    postRequest.put("price", PRICE_OFFER_SCHNITZELMENUE_AS_MONEY.getValue());

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
    assertThat(responseOrderPositionEto.getOfferId()).isEqualTo(ID_OFFER_SCHNITZELMENUE);
    assertThat(responseOrderPositionEto.getOfferName()).isEqualTo(NAME_OFFER_SCHNITZELMENUE);
    assertThat(responseOrderPositionEto.getState()).isEqualTo(STATE_ORDERPOSITION);
    assertThat(responseOrderPositionEto.getDrinkState()).isEqualTo(STATE_PRODUCT_DRINK);
    assertThat(responseOrderPositionEto.getPrice()).isEqualTo(PRICE_OFFER_SCHNITZELMENUE_AS_MONEY);
    assertThat(responseOrderPositionEto.getComment()).isEqualTo(COMMENT_ORDERPOSITION);
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
