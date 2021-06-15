package com.lts.service;

import com.lts.model.wrapper.UserResource;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    UserResource getUserResource(UserDetails userDetails);

    boolean hasPrivilege(UserDetails userDetails, String idPrivilegeDetail);

    boolean validCredentials(UserDetails userDetails);
}
