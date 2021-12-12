package com.lts.controller;

import com.lts.model.entities.PrivilegeNode;
import com.lts.service.UserPrivilegesService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@Api(tags = "User privilege")
@RestController
@RequestMapping(path = "/api/v1/privileges/users")
public class UserPrivilegesController {

    private final UserPrivilegesService userPrivilegesService;

    public UserPrivilegesController(UserPrivilegesService userPrivilegesService) {
        this.userPrivilegesService = userPrivilegesService;
    }

    @GetMapping("/{userId}")
    public @ResponseBody
    Collection<PrivilegeNode> getUserPrivilegesTree(@PathVariable int userId) {
        return userPrivilegesService.getUserPrivilegesTree(userId);
    }

    @PostMapping("/{userId}")
    public void updateUserPrivileges(@PathVariable long userId,
                                     @RequestBody List<Long> privileges) {
        userPrivilegesService.replaceUserPrivileges(userId, privileges);
    }

}