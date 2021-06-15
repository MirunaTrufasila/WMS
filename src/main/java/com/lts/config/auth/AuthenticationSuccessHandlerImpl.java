package com.lts.config.auth;

import com.lts.model.entities.User;
import com.lts.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * I overrided this class to allow the last login update only after we confirmed a successful login attempt.
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final String redirectPath;

    public AuthenticationSuccessHandlerImpl(UserRepository userRepository,
                                            String redirectPath) {
        this.userRepository = userRepository;
        this.redirectPath = redirectPath;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        User user = userRepository.getByUsername(authentication.getName());
        request.getSession().setAttribute("currentUserEditUrl", request.getContextPath() + "/users/edit/" + user.getId());
        // error code is removed on successful login
        request.getSession().removeAttribute("loginErrorCode");

        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, StringUtils.defaultString(redirectPath, "/"));
    }
}
