package com.lts.controller;

import com.lts.model.entities.User;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
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
import com.lts.service.UserService;

@Api(tags = "User")
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public @ResponseBody
    Page<User> get(@RequestParam int pageNumber,
                   @RequestParam int pageSize) {
        return userService.get(pageNumber, pageSize);
    }

    @GetMapping("/filter")
    public @ResponseBody
    Page<User> filter(@RequestParam String filter,
                      @RequestParam Object value,
                      @RequestParam int pageNumber,
                      @RequestParam int pageSize) {
        return userService.filter(filter, value, pageNumber, pageSize);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @AuthenticationPrincipal UserDetails userDetails) {
        userService.delete(id, userDetails);
    }

}