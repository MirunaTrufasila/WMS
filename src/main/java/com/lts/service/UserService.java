package com.lts.service;

import com.lts.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    Page<User> filter(String filter, Object value, int pageNumber, int pageSize);

    Page<User> get(int pageNumber, int pageSize);

    User create(User user);

    User update(User user);

    void delete(Long id, UserDetails userDetails);

    User getUser(Long id);

    User getByUsername(String username);

    User findById(Long idUser);

}
