package com.lts.service;

import com.lts.model.entities.PrivilegeNode;

import java.util.Collection;
import java.util.List;

public interface UserPrivilegesService {

    Collection<PrivilegeNode> getUserPrivilegesTree(long userId);

    void replaceUserPrivileges(long idUser, List<Long> privilegesIds);

}
