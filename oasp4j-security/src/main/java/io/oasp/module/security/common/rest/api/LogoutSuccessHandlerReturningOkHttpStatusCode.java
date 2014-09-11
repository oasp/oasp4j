package io.oasp.module.security.common.rest.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutSuccessHandlerReturningOkHttpStatusCode implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
