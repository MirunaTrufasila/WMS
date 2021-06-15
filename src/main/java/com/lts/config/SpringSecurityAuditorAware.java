package com.lts.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Awesome tool to set the createdBy and updatedBy automatically!
 * For this to work, the logged user name must be detected as seen below and this bean needs to be
 * loaded in config and specified in {@link org.springframework.data.jpa.repository.config.EnableJpaAuditing} interface ref.
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof UserDetails)) {
            return Optional.empty();
        }
        return Optional.of(((UserDetails) authentication.getPrincipal()).getUsername());
    }
}
