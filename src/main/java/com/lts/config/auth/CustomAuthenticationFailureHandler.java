package com.lts.config.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException ex) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        // exception message is the error code, to be displayed on UI
        request.getSession().setAttribute("loginErrorCode", ex.getMessage());

        response.sendRedirect(request.getContextPath() + "/login?error");
    }
}