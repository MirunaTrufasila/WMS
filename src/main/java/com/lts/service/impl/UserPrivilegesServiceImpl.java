package com.lts.service.impl;

import com.lts.model.entities.Privilege;
import com.lts.model.entities.PrivilegeNode;
import com.lts.model.entities.UserPrivilege;
import com.lts.repository.PrivilegeRepository;
import com.lts.repository.UserPrivilegeRepository;
import com.lts.service.MessageService;
import com.lts.service.UserPrivilegesService;
import com.lts.util.UserPrivilegesUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserPrivilegesServiceImpl implements UserPrivilegesService {

    private final PrivilegeRepository privilegeRepository;
    private final UserPrivilegeRepository userPrivilegeRepository;
    private final MessageService messageService;

    public UserPrivilegesServiceImpl(PrivilegeRepository privilegeRepository,
                                     UserPrivilegeRepository userPrivilegeRepository,
                                     MessageService messageService) {
        this.privilegeRepository = privilegeRepository;
        this.userPrivilegeRepository = userPrivilegeRepository;
        this.messageService = messageService;
    }


    @Override
    public Collection<PrivilegeNode> getUserPrivilegesTree(long userId) {
        List<Privilege> allPrivileges = privilegeRepository.findAll(new Sort(Sort.Direction.ASC, "idParinte", "id"));
        List<UserPrivilege> allUserPrivileges = userPrivilegeRepository.findByIdUser(userId);
        Map<Long, Boolean> privilegesMap = new HashMap<>();
        for (UserPrivilege privilege : allUserPrivileges) {
            privilegesMap.put(privilege.getIdPrivilege(), Boolean.TRUE);
        }
        return UserPrivilegesUtil.getPrivilegeTree(allPrivileges, privilegesMap, messageService);
    }


    @Override
    public void replaceUserPrivileges(long userId, List<Long> privilegesIds) {
        List<UserPrivilege> userPrivileges = new ArrayList<>();
        for (Long privilegeId : privilegesIds) {
            userPrivileges.add(new UserPrivilege(userId, privilegeId));
        }
        userPrivilegeRepository.deleteAllByIdUser(userId);
        userPrivilegeRepository.saveAll(userPrivileges);
    }

}
