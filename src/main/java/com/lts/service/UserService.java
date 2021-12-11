package com.lts.service;

import com.lts.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    Page<User> filterUser(String filter, Object value, int pageNumber, int pageSize);

    Page<User> getUsers(int pageNumber, int pageSize);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id, UserDetails userDetails);

    User getUser(Long id);

}
