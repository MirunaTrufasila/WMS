package com.lts.controller;

import com.lts.config.auth.ApplicationPrivilege;
import com.lts.model.entities.User;
import com.lts.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "User")
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(null, " + ApplicationPrivilege.USERS_EDIT + ")")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    @PreAuthorize("hasPermission(null, " + ApplicationPrivilege.USERS + ")")
    public @ResponseBody
    Page<User> getUsers(@RequestParam int pageNumber,
                        @RequestParam int pageSize) {
        return userService.getUsers(pageNumber, pageSize);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasPermission(null, " + ApplicationPrivilege.USERS + ")")
    public @ResponseBody
    Page<User> filterUsers(@RequestParam String filter,
                           @RequestParam Object value,
                           @RequestParam int pageNumber,
                           @RequestParam int pageSize) {
        return userService.filterUser(filter, value, pageNumber, pageSize);
    }

    @PostMapping
    @PreAuthorize("hasPermission(null, " + ApplicationPrivilege.USERS_ADD + ")")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    @PreAuthorize("hasPermission(null, " + ApplicationPrivilege.USERS_EDIT + ")")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(null, " + ApplicationPrivilege.USERS_DEL + ")")
    public void deleteUser(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteUser(id, userDetails);
    }

}