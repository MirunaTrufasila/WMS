package com.lts.service;

import com.lts.model.wrapper.ApplicationProperties;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminService {

    ApplicationProperties getApplicationProperties(UserDetails userDetails);

    boolean isDevelopment();

    boolean isLocalProfile();

    String getContextPath();

    boolean isTestProfile();
}
