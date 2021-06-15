package com.lts.config.aop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lts.model.entities.UserActivity;
import com.lts.repository.UserActivityRepository;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.thymeleaf.util.MapUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ControllerAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final static String ACCEPT_HEADER = "accept";
    private final static String ACCEPT_LANG_HEADER = "accept-language";
    private final static String USER_AGENT_HEADER = "user-agent";
    private final static List<String> ignoredServlets;
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    static {
        ignoredServlets = new ArrayList<>();
        ignoredServlets.add("/api/v1/admin");
        ignoredServlets.add("/auth");
    }

    private final UserActivityRepository userActivityRepository;

    public ControllerAspect(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    @Around("within(com.lts.LiteTechnologiesSoftware.controller..*)")
    public Object logControllerAccess(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object value;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
        try {

            final String servletPath = request.getServletPath();
            for (String ignoredServlet : ignoredServlets) {
                if (StringUtils.containsIgnoreCase(servletPath, ignoredServlet)) {
                    return proceedingJoinPoint.proceed();
                }
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // TODO maybe skip unauthenticated calls?
            String currentPrincipalName = authentication != null ? authentication.getName() : "userNecunoscut";

            UserActivity userActivity = new UserActivity();
            userActivity.setUsername(currentPrincipalName);
            userActivity.setServletPath(servletPath);
            if ("POST".equals(request.getMethod()) && request.getQueryString() == null) {
                if (!MapUtils.isEmpty(request.getParameterMap())) {
                    userActivity.setRequestParams(OBJECT_MAPPER.writeValueAsString(request.getParameterMap()));
                }
            } else {
                userActivity.setRequestParams(request.getQueryString());
            }
            userActivity.setContextPath(request.getContextPath());
            userActivity.setAccept(request.getHeader(ACCEPT_HEADER));
            userActivity.setLang(request.getHeader(ACCEPT_LANG_HEADER));
            userActivity.setUserAgent(request.getHeader(USER_AGENT_HEADER));
            userActivity.setMethod(request.getMethod());
            userActivity.setRemoteAddress(request.getRemoteAddr());
            userActivity.setRemotePort(request.getRemotePort());
            userActivity.setRemoteUser(request.getRemoteUser());
            userActivity.setCreatedAt(LocalDateTime.now());

            if (request instanceof ContentCachingRequestWrapper) {
                byte[] bodyContent = ((ContentCachingRequestWrapper) request).getContentAsByteArray();
                if (bodyContent != null) {
                    userActivity.setRequestBody(IOUtils.toString(bodyContent, "UTF-8"));
                }
            } else {
                if (request.getReader() != null) {
                    userActivity.setRequestBody(IOUtils.toString(request.getReader()));
                }
            }

            userActivityRepository.save(userActivity);

            value = proceedingJoinPoint.proceed();
        } finally {
            if (log.isDebugEnabled()) {
                log.debug(
                        "{} {} from {}",
                        request.getMethod(),
                        request.getRequestURI(),
                        request.getRemoteAddr(),
                        request.getRemoteUser());
            }
        }

        return value;
    }
}
