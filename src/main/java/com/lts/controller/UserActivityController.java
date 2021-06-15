package com.lts.controller;

import com.lts.model.entities.UserActivity;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lts.service.UserActivityService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "User activity")
@RestController
@RequestMapping(path = "/api/v1/activities")
public class UserActivityController {

    private final UserActivityService userActivityService;

    public UserActivityController(UserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @GetMapping("/{id}")
    public UserActivity getOne(@PathVariable Long id) {
        return userActivityService.getUserActivity(id);
    }

    @PostMapping("/filter")
    public Page<UserActivity> filter(
            HttpServletRequest request,
            @RequestBody Map<String, Object> filters,
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {
        return userActivityService.filter(request, filters, pageNumber, pageSize);
    }

}
