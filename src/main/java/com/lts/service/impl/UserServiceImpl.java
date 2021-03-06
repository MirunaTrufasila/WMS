package com.lts.service.impl;

import com.lts.config.auth.CustomPasswordEncoder;
import com.lts.controller.exception.ValidationException;
import com.lts.model.entities.User;
import com.lts.model.filters.UserFilter;
import com.lts.repository.UserRepository;
import com.lts.service.MessageService;
import com.lts.service.UserPrivilegesService;
import com.lts.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;
    private final MessageService messageService;
    private final UserPrivilegesService userPrivilegeService;

    public UserServiceImpl(UserRepository userRepository,
                           CustomPasswordEncoder passwordEncoder,
                           MessageService messageService,
                           UserPrivilegesService userPrivilegeService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageService = messageService;
        this.userPrivilegeService = userPrivilegeService;
    }

    @Override
    public Page<User> filterUser(String filter, Object value, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber - 1, pageSize);
        Page<User> pageUsers;
        if (StringUtils.equals(filter, UserFilter.NAME.name()) && value != null && !value.toString().isEmpty()) {
            pageUsers = userRepository.getByUsernameContainsIgnoreCase(page, value.toString());
        } else {
            pageUsers = userRepository.findAll(page);
        }
        return pageUsers;
    }


    @Override
    public Page<User> getUsers(int pageNumber, int pageSize) {
        return filterUser(null, null, pageNumber, pageSize);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        if (user.getId() > 0) {
            throw new ValidationException(user, messageService.getMessage("errUserCannotHaveId"));
        }
        validationUser(user);
        user = userRepository.save(user);

        return user;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (user.getId() == 0) {
            throw new ValidationException(user, messageService.getMessage("errIdNotSet"));
        }
        validationUser(user);
        return userRepository.save(user);
    }

    private void validationUser(User user) {
        if (user.getPasswordUpdate() != null) {
            user.setPassword(passwordEncoder.encode(user.getPasswordUpdate()));
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new ValidationException(user, messageService.getMessage("errUsernameEmpty"));
        }
        User testUser = userRepository.getByUsername(user.getUsername());
        if (testUser != null && testUser.getId() != user.getId()) {
            throw new ValidationException(user, messageService.getMessage("errUserNameAlreadyExist"));
        }
    }

    @Override
    public void deleteUser(Long userId, UserDetails userDetails) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", userId));
        }
        userRepository.delete(user);
    }

    @Override
    public User getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", id));
        }
        return user;
    }

}