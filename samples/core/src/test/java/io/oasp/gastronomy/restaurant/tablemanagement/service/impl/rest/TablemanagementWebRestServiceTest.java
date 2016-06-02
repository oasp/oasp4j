package io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
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

public class TablemanagementWebRestServiceTest extends SubsystemTest {

  // reads from test/resources/config/application.properties the variable server.port
  @Value("${local.server.port}")
  private int port;

  // TODO Inject ...
  private RestTemplate template;

  @Inject
  private Flyway flyway;

  private HttpHeaders authentificatedHeaders;

  public TablemanagementWebRestServiceTest() {
    this.template = new RestTemplate();
    this.authentificatedHeaders = getAuthentificatedHeaders();
  }

  @Before
  public void prepareTest() {

    this.flyway.clean();
    this.flyway.migrate();
  }

  @Test
  public void updateTable() {

    // getRequestBefore
    HttpEntity<String> getRequestEntity = new HttpEntity<String>(this.authentificatedHeaders);
    long tableId = 102;

    ResponseEntity<String> getResponse = this.template.exchange(
        "http://localhost:" + this.port + "/services/rest" + "/tablemanagement/v1/table/" + Long.toString(tableId),
        HttpMethod.GET, getRequestEntity, String.class);
    String getResponseString = getResponse.getBody();
    assertThat(getResponse).isNotNull();
    assertThat(getResponseString).isNotNull();
    System.out.println("-----------------getRequestBefore-----------------");
    System.out.println(getResponse);
    System.out.println(getResponseString);

    // putRequest
    HttpHeaders postRequestHeaders = this.authentificatedHeaders;
    postRequestHeaders.setContentType(MediaType.APPLICATION_JSON);
    String requestPayload =
        "{\"id\": 102, \"modificationCounter\": 1, \"revision\": null, \"waiterId\": null, \"number\": 2, \"state\": \"OCCUPIED\"}";
    HttpEntity<String> postRequestEntity = new HttpEntity<String>(requestPayload, postRequestHeaders);

    ResponseEntity<String> postResponse =
        this.template.exchange("http://localhost:" + this.port + "/services/rest" + "/tablemanagement/v1/table/",
            HttpMethod.POST, postRequestEntity, String.class);
    String postResponseString = postResponse.getBody();
    assertThat(postResponse).isNotNull();
    assertThat(postResponseString).isNotNull();
    System.out.println("-----------------postRequestHeaders-----------------");
    System.out.println(postResponse);
    System.out.println(postResponseString);

    // getRequestAfter
    getRequestEntity = new HttpEntity<String>(this.authentificatedHeaders);

    getResponse = this.template.exchange(
        "http://localhost:" + this.port + "/services/rest" + "/tablemanagement/v1/table/" + Long.toString(tableId),
        HttpMethod.GET, getRequestEntity, String.class);
    getResponseString = getResponse.getBody();
    assertThat(getResponse).isNotNull();
    assertThat(getResponseString).isNotNull();
    System.out.println("-----------------getRequestAfter-----------------");
    System.out.println(getResponse);
    System.out.println(getResponseString);

  }

  // @Test
  // public void testHttpConnection() {
  //
  // String result = this.template.getForObject("http://localhost:" + this.port + "/services/rest", String.class,
  // "waiter", "waiter");
  //
  // System.out.println(result);
  // assertThat(result).isNotNull();
  //
  // /*
  // * RequestEntity request = RequestEntity.get(new URI("http://example.com/foo")).accept(MediaType.APPLICATION_JSON)
  // * .body(mapper.writeValueAsBytes(userJson)); ResponseEntity<String> response = this.template.exchange(request,
  // * String.class);
  // */
  //
  // }

  public HttpHeaders getAuthentificatedHeaders() {

    String plainCreds = "chief:chief";
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    return headers;
  }

}
