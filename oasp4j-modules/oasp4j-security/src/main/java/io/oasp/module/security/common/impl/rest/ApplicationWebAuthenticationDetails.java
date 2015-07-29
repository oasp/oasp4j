package io.oasp.module.security.common.impl.rest;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class to extract language information from the request. This class try to extract language information in the follow
 * order: 1. From request paramenter (GET, POST, BODY) 2. From request headers
 *
 * @author dsanche2
 */
public class ApplicationWebAuthenticationDetails extends WebAuthenticationDetails {

  private static final long serialVersionUID = 310L;

  private static final String LANGUAGE_PARAM = "language";

  private final Locale language;

  /**
   * The constructor.
   *
   * @param request Received request
   */
  public ApplicationWebAuthenticationDetails(HttpServletRequest request) {

    super(request);
    this.language = getUserLocale(request);

  }

  /**
   * @return the language
   */
  public Locale getLanguage() {

    return this.language;
  }

  /**
   * Get locale from the request
   *
   * @param request
   * @return locale User locale
   */
  private Locale getUserLocale(HttpServletRequest request) {

    Locale userLanguague = null;

    // We find locale in a request param
    if (StringUtils.hasText(request.getParameter(LANGUAGE_PARAM))) {
      userLanguague = LocaleUtils.toLocale(request.getParameter(LANGUAGE_PARAM));
    }
    // We find locale in a request body
    else {

      try {
        ObjectMapper objectMapper = new ObjectMapper();
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
        JsonNode json = objectMapper.readTree(servletServerHttpRequest.getBody());

        if (json.has(LANGUAGE_PARAM)) {
          JsonNode languageNode = json.get(LANGUAGE_PARAM);
          if (languageNode != null) {
            userLanguague = LocaleUtils.toLocale(languageNode.asText());
          }
        }
      } catch (IOException e) {
        // ignoring
      }

    }

    // By default, as a last option, we get locale from request headers
    if (userLanguague == null) {
      userLanguague = request.getLocale();
    }

    return userLanguague;
  }

}