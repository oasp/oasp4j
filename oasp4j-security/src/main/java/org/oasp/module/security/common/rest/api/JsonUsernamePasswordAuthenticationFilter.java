package org.oasp.module.security.common.rest.api;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private String usernameParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean postOnly = true;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JsonUsernamePasswordAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException,
            IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        final UsernameAndPasswordParser usernameAndPasswordParser = new UsernameAndPasswordParser(request);
        usernameAndPasswordParser.parse();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                usernameAndPasswordParser.getTrimmedUsername(), usernameAndPasswordParser.getPassword());
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    public String getUsernameParameter() {
        return usernameParameter;
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    public String getPasswordParameter() {
        return passwordParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    public boolean isPostOnly() {
        return postOnly;
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
            password = extractValueByName(passwordParameter);
        }

        private void extractUsername() {
            username = extractValueByName(usernameParameter);
        }

        private String extractValueByName(String name) {
            String value = null;
            if (credentialsNode.has(name)) {
                JsonNode node = credentialsNode.get(name);
                if (node != null) {
                    value = node.asText();
                }
            }
            return value;
        }

        private boolean jsonParsedSuccessfully() {
            return credentialsNode != null;
        }

        private void parseJsonFromRequestBody() {
            try {
                final ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
                credentialsNode = objectMapper.readTree(servletServerHttpRequest.getBody());
            }
            catch (IOException e) {
                // ignoring
            }
        }

        private String getTrimmedUsername() {
            return username == null ? "" : username.trim();
        }

        private String getPassword() {
            return password == null ? "" : password;
        }
    }
}
