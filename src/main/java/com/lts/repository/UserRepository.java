package com.lts.repository;

import com.lts.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByUsername(String username);

    Page<User> getByUsernameContainsIgnoreCase(Pageable page, String nume);

}