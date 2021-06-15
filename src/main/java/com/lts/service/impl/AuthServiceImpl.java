package com.lts.service.impl;

import com.lts.model.entities.User;
import com.lts.model.wrapper.UserResource;
import com.lts.repository.UserRepository;
import com.lts.service.AuthService;
import com.lts.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final MessageService messageService;

    public AuthServiceImpl(EntityManager entityManager,
                           UserRepository userRepository,
                           MessageService messageService) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.messageService = messageService;
    }

    @Override
    public UserResource getUserResource(UserDetails userDetails) {
        if (!validCredentials(userDetails)) {
            throw new AuthenticationCredentialsNotFoundException(messageService.getMessage("errNotAutenticated"));
        }

        UserResource userResource = new UserResource();
        userResource.setUserName(userDetails.getUsername());
        User user = userRepository.getByUsername(userDetails.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException(messageService.getMessage("errUserNotFound", userDetails.getUsername()));
        }
        userResource.setName(user.getFirstName());
        Query q = entityManager.createQuery("SELECT idPrivilege FROM UserPrivilege WHERE idUser = :idUser");
        q.setParameter("idUser", user.getId());
        List existingPrivileges = q.getResultList();
        List<String> privilegesAsString = new ArrayList<>();
        for (Object drept : existingPrivileges) {
            privilegesAsString.add(drept.toString());
        }
        userResource.setPermissions(privilegesAsString);
        return userResource;
    }

    @Override
    public boolean hasPrivilege(UserDetails userDetails, String idPrivilege) {
        if (!validCredentials(userDetails)) {
            return false;
        }

        // TODO this is used for test profile - nasty hack, remove in the future
        if ("admin".equals(userDetails.getUsername())) {
            return true;
        }
        User user = userRepository.getByUsername(userDetails.getUsername());
        if (user == null) {
            logger.error("User with username {} doesn't exist!", userDetails.getUsername());
            return false;
        }

        try {
            Long.parseLong(idPrivilege);
        } catch (Exception exc) {
            logger.error("Invalid privilege id: {}", idPrivilege);
            return false;
        }

        Query privilegeCountQuery = entityManager.createQuery("select count(1) from UserPrivilege where idPrivilege = :idPrivilege and idUser = :idUser");
        privilegeCountQuery.setParameter("idPrivilege", Long.parseLong(idPrivilege));
        privilegeCountQuery.setParameter("idUser", user.getId());
        return ((Long) privilegeCountQuery.getSingleResult()) > 0;
    }

    @Override
    public boolean validCredentials(UserDetails userDetails) {
        return userDetails != null
                && userDetails.isCredentialsNonExpired()
                && userDetails.isEnabled()
                && userDetails.isAccountNonExpired()
                && userDetails.isAccountNonLocked();
    }
}
