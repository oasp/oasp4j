package io.oasp.gastronomy.restaurant.general.common.api.security;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.base.AbstractRestServiceTest;

/**
 * TODO jmolinar This type ...
 *
 * @author jmolinar
 * @since dev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootApp.class)
@TestPropertySource(properties = { "flyway.locations=filesystem:src/test/resources/db/tablemanagement" })
public class UserDataTest extends AbstractRestServiceTest {

  @Configuration
  static class MyConfig {
    @Bean
    public RestTemplate restTemplate() {

      RestTemplate restTemplate = new RestTemplate();
      return restTemplate;
    }
  }

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UserDataTest.class);

  @Inject
  private JacksonJsonProvider jacksonJsonProvider;

  @Inject
  private RestTemplate template;

  @Test
  @Ignore
  public void testGet() {

    String username = "waiter";
    String password = "waiter";
    List<GrantedAuthority> authorities = new ArrayList<>();
    User principal = new User(username, password, true, true, true, true, authorities);

    Authentication authentication = new UsernamePasswordAuthenticationToken(principal, password);

    UserData userData = UserData.get(authentication);

  }

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

  private enum MT {
    JSON, XML
  }

  private HttpHeaders prepareHeaders(MT mt, StrTup... stringTuples) {

    HttpHeaders result = new HttpHeaders();
    if (stringTuples != null) {

      for (StrTup t : stringTuples) {
        result.add(t.getKey(), t.getValue());
      }
    }
    if (mt != null) {
      result.setContentType(
          mt == MT.JSON ? MediaType.APPLICATION_JSON : (mt == MT.XML ? MediaType.APPLICATION_XML : null));
    }
    return result;
  }

  @Test

  public void testLogin() {

    String tmpUrl = "http://localhost:" + String.valueOf(this.port) + "/services/rest/login";
    String userName = "waiter";
    String tmpPassword = "waiter";

    HttpEntity<String> postRequest = new HttpEntity<>(
        prepareJson(new StrTup("j_username", userName), new StrTup("j_password", tmpPassword)), prepareHeaders(MT.XML));

    ResponseEntity<String> postResponse = this.template.exchange(tmpUrl, HttpMethod.POST, postRequest, String.class);
    LOG.debug("Body: " + postResponse.getBody());
    assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    // Prepare CSRF header

    // String csrfToken = "";
    // String encodedCsrfToken = new String(Base64.encodeBase64(csrfToken.getBytes()));
    // headers.add("X-CSRF-TOKEN", encodedCsrfToken);
    // tmpUrl = "http://localhost:" + String.valueOf(this.port) + "/services/rest/security/v1/currentuser";
    // HttpEntity<String> entity = new HttpEntity<>(prepareHeaders(MT.JSON, null));
    // ResponseEntity<String> responseEntity = this.template.getForEntity(tmpUrl, String.class);
    // LOG.debug("GET Response: " + responseEntity.getBody());
  }

  @Test

  @Ignore
  public void testGetCurrentUser() {

    // String tmpUrl = "http://localhost:" + String.valueOf(this.port) + "/services/rest/security/v1/currentuser";
    // HttpEntity<String> entity = new HttpEntity<>(prepareHeaders(MT.JSON, new StrTup(null)));
    // ResponseEntity<String> responseEntity = this.template.getForEntity(tmpUrl, String.class);
    // LOG.debug("GET Response User: " + responseEntity.getBody());

  }

  @Test
  public void testGetCsrfToken() {

    String tmpUrl = "http://localhost:" + String.valueOf(this.port) + "/services/rest/login";
    String userName = "waiter";
    String tmpPassword = "waiter";

    HttpEntity<String> postRequest =
        new HttpEntity<>(prepareJson(new StrTup("j_username", userName), new StrTup("j_password", tmpPassword)),
            prepareHeaders(MT.JSON));

    ResponseEntity<String> postResponse = this.template.exchange(tmpUrl, HttpMethod.POST, postRequest, String.class);

    LOG.debug("Body: " + postResponse.getBody());
    assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    List<String> setCookie = postResponse.getHeaders().get("Set-Cookie");
    assertThat(setCookie).hasSize(1);
    String jsessionId = setCookie.get(0);
    tmpUrl = "http://localhost:" + String.valueOf(this.port) + "/services/rest/security/v1/csrftoken";
    StrTup cookieHeader = new StrTup(HttpHeaders.COOKIE, jsessionId);
    HttpHeaders headers = prepareHeaders(MT.JSON, cookieHeader);

    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<String> responseEntity = this.template.exchange(tmpUrl, HttpMethod.GET, entity, String.class);
    String csrfTokenBody = responseEntity.getBody();
    LOG.debug("GET Response Csrf: " + csrfTokenBody);
    String csrfToken = csrfTokenBody.split(",")[0].split(":")[1].split("\"")[1];
    tmpUrl = "http://localhost:" + String.valueOf(this.port) + "/services/rest/security/v1/currentuser";
    entity = new HttpEntity<>(prepareHeaders(MT.JSON, cookieHeader, new StrTup("X-CSRF-TOKEN", csrfToken)));
    responseEntity = this.template.exchange(tmpUrl, HttpMethod.GET, entity, String.class);
    LOG.debug("GET Response User: " + responseEntity.getBody());
  }

}
