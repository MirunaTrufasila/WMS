package com.lts.repository;

import com.lts.model.entities.UserPrivilege;
import com.lts.model.entities.UserPrivilegeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, UserPrivilegeId> {

    List<UserPrivilege> findByIdUser(long idUser);
}