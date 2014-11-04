#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.general.webclient;

import ${package}.general.service.impl.rest.ApplicationObjectMapperFactory;
import ${package}.test.general.ExampleAppTestProperties;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * TODO hahmad This type ...
 *
 * @author hahmad
 */
public class WebClientWrapper {

  private WebClient webClient;

  private String username;

  private String password;

  private boolean isCsrfHeaderSet;

  public WebClientWrapper(String username, String password) {

    this.username = username;
    this.password = password;
    this.isCsrfHeaderSet = false;
    initWebClient();
  }

  public WebClient getWebClient() {

    return this.webClient;
  }

  private void initWebClient() {

    List<Object> providers = new ArrayList<Object>();

    JacksonJaxbJsonProvider j = new JacksonJaxbJsonProvider();
    j.setMapper(new ApplicationObjectMapperFactory().createInstance());
    providers.add(j);

    WebClient client = WebClient.create(ExampleAppTestProperties.hostUrl, providers);

    WebClient.getConfig(client).getRequestContext().put(org.apache.cxf.message.Message.MAINTAIN_SESSION, Boolean.TRUE);

    // set http accept header and type header
    client = client.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);

    client =
        client.path("/j_spring_security_login").query("j_username", this.username).query("j_password", this.password);
    Response res = client.post("");

    // TODO Handle when login fails
    int status2 = res.getStatus();
    System.out.println("Http status " + status2);

    this.webClient = client;
  }

  public String getUsername() {

    return this.username;
  }

  public void setCsrfHeader() {

    String myToken;
    this.webClient = this.webClient.replacePath("/services/rest/security/csrfToken/");

    // Response response = this.webClient.get();

    String res = this.webClient.get(String.class);
    String[] ar = res.replace("{", "").replace("}", "").replace("${symbol_escape}"", "").split(",");

    myToken = ar[0].split(":")[1];

    this.webClient = this.webClient.header("X-CSRF-TOKEN", myToken);

    this.isCsrfHeaderSet = true;
  }

  /**
   * @return isCsrfHeaderSet
   */
  public boolean isCsrfHeaderSet() {

    return this.isCsrfHeaderSet;
  }

}
