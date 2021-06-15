package com.lts.controller;

import com.lts.model.wrapper.ApplicationProperties;
import com.lts.service.AdminService;
import io.swagger.annotations.Api;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Administration")
@RestController
@RequestMapping(path = "/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/properties")
    public ApplicationProperties get(@AuthenticationPrincipal UserDetails userDetails) {
        return adminService.getApplicationProperties(userDetails);
    }

}