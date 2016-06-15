package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.BASE_URL_PRAEFIX;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.BASE_URL_SUFFIX_1;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.BASE_URL_SUFFIX_2;
import static io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTestHelper.INITIAL_NUMBER_OF_ORDERS;
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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderEto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.module.basic.configuration.OaspProfile;
import io.oasp.module.test.common.base.SubsystemTest;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { SpringBootApp.class, SalesmanagementRestTestConfiguration.class })
@WebIntegrationTest("server.port:0")
@ActiveProfiles(profiles = { OaspProfile.JUNIT_TEST })
@Transactional

public class SalesmanagementWebRestServiceTest extends SubsystemTest {

  @Value("${local.server.port}")
  private int port;

  @Inject
  private SalesmanagementRestServiceTestHelper helper;

  private final HttpHeaders AUTHENTIFICATED_HEADERS = getAuthentificatedHeaders();

  // TODO Inject ...
  private RestTemplate template;

  public SalesmanagementWebRestServiceTest() {

    this.template = new RestTemplate();
  }

  @PostConstruct
  public void beforeTest() {

    this.helper.init(this.port);
  }

  @Test
  public void getOrder() {

    // given
    HttpEntity<String> getRequest = new HttpEntity<>(this.AUTHENTIFICATED_HEADERS);
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.helper.getService().saveOrder(sampleOrderCto);

    // when
    ResponseEntity<String> getResponse = this.template.exchange(
        // TODO change id like this in other requests
        generateBaseUrl() + "order/" + responseOrderCto.getOrder().getId(), HttpMethod.GET, getRequest, String.class);

    // then
    assertThat(getResponse).isNotNull();
    String getResponseJson = getResponse.getBody();
    JSONAssert.assertEquals("{id:" + responseOrderCto.getOrder().getId() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{tableId:" + Long.toString(SAMPLE_TABLE_ID) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{tableId:" + Long.toString(responseOrderCto.getOrder().getTableId()) + "}",
        getResponseJson, false);

  }

  // @Test
  // public void getOrderWithPositions() {
  //
  // // given
  // HttpEntity<String> getRequest = new HttpEntity<>(this.AUTHENTIFICATED_HEADERS);
  // OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
  // this.helper.getService().saveOrder(sampleOrderCto);
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
  public void postOrderWithPosition() {

    // given
    HttpHeaders postRequestHeaders = this.AUTHENTIFICATED_HEADERS;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    JSONArray jsonArray = new JSONArray();
    JSONObject innerObject = new JSONObject();
    innerObject.put("tableId", SAMPLE_TABLE_ID);
    JSONObject request = new JSONObject();

    JSONObject orderPosition = new JSONObject();

    // orderPosition.put("orderId", INITIAL_NUMBER_OF_ORDERS + 6);
    orderPosition.put("offerId", SAMPLE_OFFER_ID);
    orderPosition.put("state", SAMPLE_ORDER_POSITION_STATE);
    orderPosition.put("drinkState", SAMPLE_DRINK_STATE);
    orderPosition.put("comment", SAMPLE_COMMENT);
    jsonArray.put(orderPosition);

    request.put("positions", jsonArray);
    request.put("order", innerObject);

    HttpEntity<String> postRequestEntity = new HttpEntity<>(request.toString(), postRequestHeaders);

    // when
    ResponseEntity<String> postResponse =
        this.template.exchange(generateBaseUrl() + "order/", HttpMethod.POST, postRequestEntity, String.class);

    // then
    // TODO get ID out of postResponse
    OrderEto expectedOrderEto = this.helper.getService().findOrder(INITIAL_NUMBER_OF_ORDERS + 1);
    OrderPositionEto expectedOrderPositionEto =
        this.helper.getService().findOrderPosition(this.helper.getNumberOfOrderPositions());

    assertThat(expectedOrderEto).isNotNull();
    assertThat(expectedOrderEto.getId()).isEqualTo(INITIAL_NUMBER_OF_ORDERS + 1);
    assertThat(expectedOrderEto.getTableId()).isEqualTo(SAMPLE_TABLE_ID);

    // TODO ask Jonas if really necessary to safe and verify an orderposition
    assertThat(expectedOrderPositionEto.getId()).isEqualTo(this.helper.getNumberOfOrderPositions());
    assertThat(expectedOrderPositionEto.getOrderId()).isEqualTo(INITIAL_NUMBER_OF_ORDERS + 1);
    assertThat(expectedOrderPositionEto.getOfferName()).isEqualTo(SAMPLE_OFFER_NAME);
    assertThat(expectedOrderPositionEto.getState()).isEqualTo(SAMPLE_ORDER_POSITION_STATE);
    assertThat(expectedOrderPositionEto.getDrinkState()).isEqualTo(SAMPLE_DRINK_STATE);
    assertThat(expectedOrderPositionEto.getPrice()).isEqualTo(SAMPLE_PRICE);
    assertThat(expectedOrderPositionEto.getComment()).isEqualTo(SAMPLE_COMMENT);

  }

  @Test
  public void getOrderPosition() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.helper.getService().saveOrder(sampleOrderCto);

    HttpEntity<String> getRequest = new HttpEntity<>(this.AUTHENTIFICATED_HEADERS);
    OrderPositionEto sampleOrderPositionEto =
        this.helper.createSampleOrderPositionEto(responseOrderCto.getOrder().getId());
    OrderPositionEto responseOrderPositionEto = this.helper.getService().saveOrderPosition(sampleOrderPositionEto);

    // when
    ResponseEntity<String> getResponse = this.template.exchange(
        generateBaseUrl() + "orderposition/" + Long.toString(this.helper.getNumberOfOrderPositions()), HttpMethod.GET,
        getRequest, String.class);

    // then
    String getResponseJson = getResponse.getBody();
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
  public void getOrderPositions() {

    // given
    OrderCto sampleOrderCto = this.helper.createSampleOrderCto(SAMPLE_TABLE_ID);
    OrderCto responseOrderCto = this.helper.getService().saveOrder(sampleOrderCto);
    HttpEntity<String> getRequest = new HttpEntity<>(this.AUTHENTIFICATED_HEADERS);

    long numberOfOrderPositions = 2;
    OrderPositionEto sampleOrderPositionEto;
    ArrayList<OrderPositionEto> responseOrderPositions = new ArrayList();
    for (int i = 0; i < numberOfOrderPositions; ++i) {
      sampleOrderPositionEto = new OrderPositionEto();
      sampleOrderPositionEto.setOrderId(responseOrderCto.getOrder().getId());
      sampleOrderPositionEto.setOfferId(SAMPLE_OFFER_ID);
      responseOrderPositions.add(this.helper.getService().saveOrderPosition(sampleOrderPositionEto));
    }
    // when
    ResponseEntity<String> getResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/", HttpMethod.GET, getRequest, String.class);

    // then
    String getResponseJson = getResponse.getBody();
    ArrayList<String> jsonObjectArrayList = buildJsonObjectArrayList(getResponseJson);

    int index = 0;

    for (String jsonObject : jsonObjectArrayList) {

      JSONAssert.assertEquals("{id:" + Long.toString(responseOrderPositions.get(index).getId()) + "}", getResponseJson,
          false);
      JSONAssert.assertEquals("{orderId:" + Long.toString(responseOrderPositions.get(index).getOrderId()) + "}",
          getResponseJson, false);
      JSONAssert.assertEquals("{offerId:" + Long.toString(responseOrderPositions.get(index).getOfferId()) + "}",
          getResponseJson, false);

      JSONAssert.assertEquals("{offerName:" + responseOrderPositions.get(index).getOfferName() + "}", getResponseJson,
          false);

      JSONAssert.assertEquals("{price:" + responseOrderPositions.get(index).getPrice() + "}", getResponseJson, false);

      index++;
    }
    assertThat(index).isEqualTo(this.helper.getNumberOfOrderPositions());
  }

  @Test
  public void putOrderPosition() {

  }

  // TODO get ID out of postResponse
  @Test
  public void postOrderPosition() {

    // given
    HttpHeaders postRequestHeaders = this.AUTHENTIFICATED_HEADERS;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    JSONObject request = new JSONObject();

    request.put("orderId", INITIAL_NUMBER_OF_ORDERS);
    request.put("offerId", SAMPLE_OFFER_ID);
    request.put("state", SAMPLE_ORDER_POSITION_STATE);
    request.put("drinkState", SAMPLE_DRINK_STATE);
    request.put("comment", SAMPLE_COMMENT);

    // both operations are redundant as the values of the attributes "offername" and "price" are persisted
    // automatically according to offerId
    // request.put("offerName", SAMPLE_OFFER_NAME); %C3%BC

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
    OrderPositionEto expectedOrderPositionEto =
        this.helper.getService().findOrderPosition(this.helper.getNumberOfOrderPositions());
    assertThat(expectedOrderPositionEto).isNotNull();
    assertThat(expectedOrderPositionEto.getId()).isEqualTo(this.helper.getNumberOfOrderPositions());
    assertThat(expectedOrderPositionEto.getOrderId()).isEqualTo(INITIAL_NUMBER_OF_ORDERS);
    assertThat(expectedOrderPositionEto.getOfferName()).isEqualTo(SAMPLE_OFFER_NAME);
    assertThat(expectedOrderPositionEto.getState()).isEqualTo(SAMPLE_ORDER_POSITION_STATE);
    assertThat(expectedOrderPositionEto.getDrinkState()).isEqualTo(SAMPLE_DRINK_STATE);
    assertThat(expectedOrderPositionEto.getPrice()).isEqualTo(SAMPLE_PRICE);
    assertThat(expectedOrderPositionEto.getComment()).isEqualTo(SAMPLE_COMMENT);
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
