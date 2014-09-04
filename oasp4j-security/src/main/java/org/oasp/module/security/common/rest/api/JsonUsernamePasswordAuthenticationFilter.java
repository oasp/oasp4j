package org.oasp.module.security.common.rest.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
  private String usernameParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

  private String passwordParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;

  private boolean postOnly = true;

  // REVIEW may-bee (hohwille) We have a centralized and custom-configured object mapper as spring bean. IMHO we should
  // inject that instance here.
  private ObjectMapper objectMapper = new ObjectMapper();

  public JsonUsernamePasswordAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {

    super(requiresAuthenticationRequestMatcher);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {

    if (this.postOnly && !request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
    }

    final UsernameAndPasswordParser usernameAndPasswordParser = new UsernameAndPasswordParser(request);
    usernameAndPasswordParser.parse();
    UsernamePasswordAuthenticationToken authRequest =
        new UsernamePasswordAuthenticationToken(usernameAndPasswordParser.getTrimmedUsername(),
            usernameAndPasswordParser.getPassword());
    authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    return getAuthenticationManager().authenticate(authRequest);
  }

  public String getUsernameParameter() {

    return this.usernameParameter;
  }

  public void setUsernameParameter(String usernameParameter) {

    this.usernameParameter = usernameParameter;
  }

  public String getPasswordParameter() {

    return this.passwordParameter;
  }

  public void setPasswordParameter(String passwordParameter) {

    this.passwordParameter = passwordParameter;
  }

  public boolean isPostOnly() {

    return this.postOnly;
  }

  public void setPostOnly(boolean postOnly) {

    this.postOnly = postOnly;
  }

  private class UsernameAndPasswordParser {
    private String username;

    private String password;

    private final HttpServletRequest request;

    private JsonNode credentialsNode;

    private UsernameAndPasswordParser(HttpServletRequest request) {

      this.request = request;
    }

    public void parse() {

      parseJsonFromRequestBody();
      if (jsonParsedSuccessfully()) {
        extractUsername();
        extractPassword();
      }
    }

    private void extractPassword() {

      this.password = extractValueByName(JsonUsernamePasswordAuthenticationFilter.this.passwordParameter);
    }

    private void extractUsername() {

      this.username = extractValueByName(JsonUsernamePasswordAuthenticationFilter.this.usernameParameter);
    }

    private String extractValueByName(String name) {

      String value = null;
      if (this.credentialsNode.has(name)) {
        JsonNode node = this.credentialsNode.get(name);
        if (node != null) {
          value = node.asText();
        }
      }
      return value;
    }

    private boolean jsonParsedSuccessfully() {

      return this.credentialsNode != null;
    }

    private void parseJsonFromRequestBody() {

      try {
        final ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(this.request);
        this.credentialsNode =
            JsonUsernamePasswordAuthenticationFilter.this.objectMapper.readTree(servletServerHttpRequest.getBody());
      } catch (IOException e) {
        // ignoring
      }
    }

    private String getTrimmedUsername() {

      return this.username == null ? "" : this.username.trim();
    }

    private String getPassword() {

      return this.password == null ? "" : this.password;
    }
  }
}
