package com.lts.config.auth;

import com.lts.util.AdminConstants;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
            throws IOException {
        if (authentication != null) {
            logger.info("Logout successful with principal: " + authentication.getName());
        }

        response.setStatus(HttpServletResponse.SC_OK);

        String loginLanguage;
        String acceptLanguageHeader = request.getHeader("Accept-Language");
        if (ArrayUtils.contains(AdminConstants.SUPPORTED_LANGUAGES, acceptLanguageHeader)) {
            loginLanguage = acceptLanguageHeader;
        } else {
            loginLanguage = AdminConstants.DEFAULT_LANGUAGE;
        }
        response.sendRedirect(request.getContextPath() + "/login?lang=" + loginLanguage);
    }

}