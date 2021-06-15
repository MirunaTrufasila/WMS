package com.lts.service.impl;

import com.lts.model.wrapper.ApplicationProperties;
import com.lts.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final Environment environment;

    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    public AdminServiceImpl(
            Environment environment) {
        this.environment = environment;
    }

    @Override
    public ApplicationProperties getApplicationProperties(UserDetails userDetails) {
        ApplicationProperties properties = new ApplicationProperties();
        return properties;
    }


    @Override
    public boolean isDevelopment() {
        if (StringUtils.contains(servletContextPath, "dev")) {
            return true;
        }
        if (environment.getActiveProfiles().length > 0) {
            return StringUtils.contains(environment.getActiveProfiles()[0], "local")
                    || StringUtils.contains(environment.getActiveProfiles()[0], "dev");
        }
        return false;
    }

    @Override
    public boolean isLocalProfile() {
        if (environment.getActiveProfiles().length > 0) {
            return StringUtils.contains(environment.getActiveProfiles()[0], "local");
        }
        return false;
    }

    @Override
    public String getContextPath() {
        return servletContextPath;
    }

    @Override
    public boolean isTestProfile() {
        if (environment.getActiveProfiles().length > 0) {
            return StringUtils.contains(environment.getActiveProfiles()[0], "test");
        }
        return false;
    }

}
