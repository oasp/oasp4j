package io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.oasp.gastronomy.restaurant.general.common.RestTestClientBuilder;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest.SalesmanagementRestService;

/**
 * TODO shuber This type ...
 *
 * @author shuber
 * @since dev
 */

public class SalesmanagementWebRestServiceTest extends SalesmanagementTest {

  private HttpEntity<String> request;

  private long numberOfOrderPositions = 0;

  private final HttpHeaders AUTHENTIFICATED_HEADERS = getAuthentificatedHeaders();

  private static final String BASE_URL_PRAEFIX = "http://localhost:";

  private static final String BASE_URL_SUFFIX = "/services/rest/salesmanagement/v1/";

  // TODO evtl. combine with EXPECTED_NUMBER_OF_ORDERS
  private static final long SAMPLE_ORDER_ID = 1L;

  // TODO talk to Jonas, that this does not work for empty strings

  private SalesmanagementRestService service;

  // TODO Inject ...
  private RestTemplate template;

  public SalesmanagementWebRestServiceTest() {
    super();
    this.template = new RestTemplate();
  }

  @Override
  @Before
  public void prepareTest() {

    this.flyway.clean();
    this.flyway.migrate();
    // cannot be put into constructor, as port is set after the constructor invocation
    this.service = RestTestClientBuilder.build(SalesmanagementRestService.class, this.ROLE, this.ROLE,
        "http://localhost:" + this.port + "/services/rest", this.jacksonJsonProvider);
    this.numberOfOrderPositions = getNumberOfOrderPositions();

  }

  @Test
  public void getOrder() {

    // setup
    HttpEntity<String> getRequest = new HttpEntity<String>(this.AUTHENTIFICATED_HEADERS);

    OrderPositionEto sampleOrderPositionEto = createSampleOrderPositionEto(SAMPLE_ORDER_ID);

    this.service.saveOrderPosition(sampleOrderPositionEto);

    // execute
    ResponseEntity<String> getResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/" + Long.toString(this.numberOfOrderPositions + 1),
            HttpMethod.GET, getRequest, String.class);

    // validate
    String getResponseJson = getResponse.getBody();
    JSONAssert.assertEquals("{id:" + Long.toString(this.numberOfOrderPositions + 1) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{orderId:" + Long.toString(SAMPLE_ORDER_ID) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{offerId:" + Long.toString(this.SAMPLE_OFFER_ID) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{offerName:" + this.SAMPLE_OFFER_NAME + "}", getResponseJson, false);
    JSONAssert.assertEquals("{state:" + this.SAMPLE_ORDER_POSITION_STATE.toString() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{drinkState:" + this.SAMPLE_DRINK_STATE.toString() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{price:" + "\"" + this.SAMPLE_PRICE.getValue() + "\"" + "}", getResponseJson, false);
    JSONAssert.assertEquals("{comment:" + this.SAMPLE_COMMENT + "}", getResponseJson, false);
  }

  @Test
  public void getOrderPosition() {

    // setup
    HttpEntity<String> getRequest = new HttpEntity<String>(this.AUTHENTIFICATED_HEADERS);

    OrderPositionEto sampleOrderPositionEto = createSampleOrderPositionEto(SAMPLE_ORDER_ID);

    this.service.saveOrderPosition(sampleOrderPositionEto);

    // execute
    ResponseEntity<String> getResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/" + Long.toString(this.numberOfOrderPositions + 1),
            HttpMethod.GET, getRequest, String.class);

    // validate
    String getResponseJson = getResponse.getBody();
    JSONAssert.assertEquals("{id:" + Long.toString(this.numberOfOrderPositions + 1) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{orderId:" + Long.toString(SAMPLE_ORDER_ID) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{offerId:" + Long.toString(this.SAMPLE_OFFER_ID) + "}", getResponseJson, false);
    JSONAssert.assertEquals("{offerName:" + this.SAMPLE_OFFER_NAME + "}", getResponseJson, false);
    JSONAssert.assertEquals("{state:" + this.SAMPLE_ORDER_POSITION_STATE.toString() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{drinkState:" + this.SAMPLE_DRINK_STATE.toString() + "}", getResponseJson, false);
    JSONAssert.assertEquals("{price:" + "\"" + this.SAMPLE_PRICE.getValue() + "\"" + "}", getResponseJson, false);
    JSONAssert.assertEquals("{comment:" + this.SAMPLE_COMMENT + "}", getResponseJson, false);
  }

  @Test
  public void getOrderPositions() {

    // setup
    HttpEntity<String> getRequest = new HttpEntity<String>(this.AUTHENTIFICATED_HEADERS);

    long index = 0;

    OrderPositionEto sampleOrderPositionEto = new OrderPositionEto();
    sampleOrderPositionEto.setOrderId(SAMPLE_ORDER_ID);
    sampleOrderPositionEto.setOfferId(this.SAMPLE_OFFER_ID);
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
  public void putOrderPosition() {

  }

  // TODO ask Jonas if this "throws" is ok: needed by URLEncoder.encode
  @Test
  public void postOrderPosition() throws JSONException, UnsupportedEncodingException {

    // setup
    HttpHeaders postRequestHeaders = this.AUTHENTIFICATED_HEADERS;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    JSONObject request = new JSONObject();

    request.put("orderId", SAMPLE_ORDER_ID);
    request.put("offerId", this.SAMPLE_OFFER_ID);
    request.put("state", this.SAMPLE_ORDER_POSITION_STATE);
    request.put("drinkState", this.SAMPLE_DRINK_STATE);
    request.put("comment", this.SAMPLE_COMMENT);

    // both operations are redundant as the values of the attributes "offername" and "price" are persisted
    // automatically according to offerId
    // request.put("offerName", this.SAMPLE_OFFER_NAME); %C3%BC
    request.put("offerName", URLEncoder.encode(this.SAMPLE_OFFER_NAME, "UTF-8"));
    request.put("price", this.SAMPLE_PRICE.getValue());

    HttpEntity<String> postRequestEntity = new HttpEntity<String>(request.toString(), postRequestHeaders);

    // execute
    ResponseEntity<String> postResponse =
        this.template.exchange(generateBaseUrl() + "orderposition/", HttpMethod.POST, postRequestEntity, String.class);

    // verify
    OrderPositionEto expectedOrderPositionEto = this.service.findOrderPosition(this.numberOfOrderPositions + 1);
    assertThat(expectedOrderPositionEto).isNotNull();
    assertThat(expectedOrderPositionEto.getId()).isEqualTo(this.numberOfOrderPositions + 1);
    assertThat(expectedOrderPositionEto.getOrderId()).isEqualTo(SAMPLE_ORDER_ID);
    assertThat(expectedOrderPositionEto.getOfferName()).isEqualTo(this.SAMPLE_OFFER_NAME);
    assertThat(expectedOrderPositionEto.getState()).isEqualTo(this.SAMPLE_ORDER_POSITION_STATE);
    assertThat(expectedOrderPositionEto.getDrinkState()).isEqualTo(this.SAMPLE_DRINK_STATE);
    assertThat(expectedOrderPositionEto.getPrice()).isEqualTo(this.SAMPLE_PRICE);
    assertThat(expectedOrderPositionEto.getComment()).isEqualTo(this.SAMPLE_COMMENT);
  }

  private HttpHeaders getAuthentificatedHeaders() {

    String plainCreds = this.ROLE + ":" + this.ROLE;
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
