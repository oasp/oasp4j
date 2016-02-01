package io.oasp.gastronomy.restaurant.general.common.impl.security;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Dies ist die Implementierung von {@link RequestMatcher}, die entscheidet, welche {@link HttpServletRequest Requests}
 * ein korrektes CSRF-Token erfordern.
 *
 * @author hohwille
 */
public class CsrfRequestMatcher implements RequestMatcher {

  private static final Pattern HTTP_METHOD_PATTERN = Pattern.compile("^GET$");

  private static final String[] PATH_PREFIXES_WITHOUT_CSRF_PROTECTION = { "/login", "/logout", "/services/rest/login", "/websocket" };

  @Override
  public boolean matches(HttpServletRequest request) {

    // GET-Anfragen sind rein lesend und erfordern keinen CSRF-Schutz
    if (HTTP_METHOD_PATTERN.matcher(request.getMethod()).matches()) {

      return false;
    }

    // Spezielle URLs bzw. Anwendungen können nicht über das Portal vor CSRF geschützt werden und kümmern sich selbst
    // darum. Das ist z.B. der Login selbst, da man das CSRF-Token erst nach dem Login abholt. Zum anderen sind das
    // die externen Anwendungen Cadenza und Foswiki, die nicht durch Adamas beeinflusst werden können.
    String requestPath = getRequestPath(request);
    for (String path : PATH_PREFIXES_WITHOUT_CSRF_PROTECTION) {
      if (requestPath.startsWith(path)) {
        return false;
      }
    }

    return true;
  }

  private String getRequestPath(HttpServletRequest request) {

    String url = request.getServletPath();
    String pathInfo = request.getPathInfo();

    if (pathInfo != null) {
      url += pathInfo;
    }

    return url;
  }
}
