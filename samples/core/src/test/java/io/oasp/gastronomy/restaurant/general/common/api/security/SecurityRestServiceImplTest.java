package io.oasp.gastronomy.restaurant.general.common.api.security;

import java.util.List;

import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.api.to.UserDetailsClientTo;
import io.oasp.gastronomy.restaurant.general.common.base.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.general.service.impl.rest.SecurityRestServiceImpl;

/**
 * This class tests the login functionality of {@link SecurityRestServiceImpl}.
 */
@SpringApplicationConfiguration(classes = SpringBootApp.class)
@TestPropertySource(properties = { "flyway.locations=filesystem:src/test/resources/db/tablemanagement" })
public class SecurityRestServiceImplTest extends AbstractRestServiceTest {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(SecurityRestServiceImplTest.class);

  private static final String _CSRF = "_csrf";

  private static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";

  private RestTemplate template = new RestTemplate();

  /**
   * Test the login functionality as it will be used from a JavaScript client.
   */
  @Test
  @DirtiesContext
  public void testLogin() {

    String userName = "waiter";
    String tmpPassword = "waiter";

    ResponseEntity<String> postResponse = login(userName, tmpPassword);
    LOG.debug("Body: " + postResponse.getBody());
    assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(postResponse.getHeaders().containsKey(HttpHeaders.SET_COOKIE)).isTrue();
  }

  /**
   * This test depends on the login and csrf functionaility. These are tested in {@link #testLogin()} and
   * {@link #testGetCsrfToken()} respectively.
   */
  @Test
  @DirtiesContext
  public void testGetCurrentUser() {

    String userName = "waiter";
    String tmpPassword = "waiter";
    String tmpUrl = "http://localhost:" + String.valueOf(this.port) + "/services/rest/security/v1/currentuser";

    ResponseEntity<String> loginResponse = login(userName, tmpPassword);

    String jsessionId = loginResponse.getHeaders().get(HttpHeaders.SET_COOKIE).get(0);
    ResponseEntity<String> csrfEntity = csrf(jsessionId);

    HttpEntity<String> entity = new HttpEntity<>(prepareHeaders(new StrTup(HttpHeaders.COOKIE, jsessionId),
        new StrTup(X_CSRF_TOKEN, new JSONObject(csrfEntity.getBody()).getString("token"))));
    ResponseEntity<UserDetailsClientTo> userEntity =
        this.template.exchange(tmpUrl, HttpMethod.GET, entity, UserDetailsClientTo.class);
    UserDetailsClientTo userDetailsClientTo = userEntity.getBody();
    assertThat(userDetailsClientTo.getId()).isNotNull();
    assertThat(userDetailsClientTo.getFirstName()).isNotEmpty();
    assertThat(userDetailsClientTo.getLastName()).isNotEmpty();
    assertThat(userDetailsClientTo.getName()).isNotEmpty();
    assertThat(userDetailsClientTo.getRole()).isNotNull();
    LOG.debug("GET Response User: " + userDetailsClientTo.getFirstName());
  }

  /**
   * This test depends on the successful login which is tested by {@link #testLogin()}. Then, a csrf token is queried
   * and checked.
   */
  @Test
  @DirtiesContext
  public void testGetCsrfToken() {

    String userName = "waiter";
    String tmpPassword = "waiter";

    ResponseEntity<String> loginResponse = login(userName, tmpPassword);

    LOG.debug("Body: " + loginResponse.getBody());
    assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    List<String> setCookie = loginResponse.getHeaders().get(HttpHeaders.SET_COOKIE);
    assertThat(setCookie).hasSize(1);
    String jsessionId = setCookie.get(0);

    ResponseEntity<String> csrfEntity = csrf(jsessionId);

    JSONObject actual = new JSONObject(csrfEntity.getBody());
    JSONObject expected =
        new JSONObject("{\"headerName\":\"" + X_CSRF_TOKEN + "\",\"parameterName\":\"" + _CSRF + "\"}");
    JSONAssert.assertEquals(expected, actual, false);
    assertThat(actual.get("token")).isNotNull();

    LOG.debug("GET Response Csrf: " + actual.toString());

  }

  /**
   * Performs a query for a csrf token.
   *
   * @param jsessionId the cookie returned after a successful login.
   * @return a {@link ResponseEntity} containing an JSON object as its body.
   */
  private ResponseEntity<String> csrf(String jsessionId) {

    String tmpUrl = "http://localhost:" + String.valueOf(this.port) + "/services/rest/security/v1/csrftoken";
    HttpHeaders headers = prepareHeaders(new StrTup(HttpHeaders.COOKIE, jsessionId));
    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<String> responseEntity = this.template.exchange(tmpUrl, HttpMethod.GET, entity, String.class);
    return responseEntity;
  }

  /**
   * Performs the login as required by a JavaScript client.
   *
   * @param userName the username of the user
   * @param tmpPassword the password of the user
   * @return @ {@link ResponseEntity} containing containing a cookie in its header.
   */
  private ResponseEntity<String> login(String userName, String tmpPassword) {

    String tmpUrl = "http://localhost:" + String.valueOf(this.port) + "/services/rest/login";

    HttpEntity<String> postRequest = new HttpEntity<>(
        prepareJson(new StrTup("j_username", userName), new StrTup("j_password", tmpPassword)), prepareHeaders());

    ResponseEntity<String> postResponse = this.template.exchange(tmpUrl, HttpMethod.POST, postRequest, String.class);
    return postResponse;
  }

  /*
   * Helpers (class and methods)
   */

  private class StrTup {
    private String key;

    private String value;

    public StrTup(String key, String value) {
      this.key = key;
      this.value = value;
    }

    /**
     * @return key
     */
    public String getKey() {

      return this.key;
    }

    /**
     * @return value
     */
    public String getValue() {

      return this.value;
    }

  }

  private String prepareJson(StrTup... stringTuples) {

    JSONObject result = new JSONObject();
    for (StrTup t : stringTuples) {
      result.put(t.getKey(), t.getValue());
    }
    return result.toString();
  }

  private HttpHeaders prepareHeaders(StrTup... stringTuples)

  {

    HttpHeaders result = new HttpHeaders();
    if (stringTuples != null) {

      for (StrTup t : stringTuples) {
        result.add(t.getKey(), t.getValue());
      }
    }
    result.setContentType(MediaType.APPLICATION_JSON);
    return result;
  }

}
