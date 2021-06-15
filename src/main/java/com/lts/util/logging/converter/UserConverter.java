package com.lts.util.logging.converter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserConverter extends ClassicConverter {

    private final static String ANONYMOUS = "anonymous";

    @Override
    public String convert(final ILoggingEvent event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ANONYMOUS;
        }
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }
}