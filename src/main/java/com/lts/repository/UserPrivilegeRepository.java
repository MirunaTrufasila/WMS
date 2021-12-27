package com.lts.repository;

import com.lts.model.entities.IdUserPrivilege;
import com.lts.model.entities.UserPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, IdUserPrivilege> {

    List<UserPrivilege> findByIdUser(long idUser);

    void deleteAllByIdUser(long idUser);
}