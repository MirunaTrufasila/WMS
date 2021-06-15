package com.lts.controller;

import com.lts.model.wrapper.UserResource;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.lts.service.AuthService;

@Api(tags = "Security")
@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/current")
    @ResponseBody
    public UserResource currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return authService.getUserResource(userDetails);
    }

    @GetMapping("/has-privilege")
    @PreAuthorize("isFullyAuthenticated()")
    public boolean hasPrivilege(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam String privilege) {
        return authService.hasPrivilege(userDetails, privilege);
    }

    @GetMapping("/valid-credentials")
    @PreAuthorize("isFullyAuthenticated()")
    public boolean validCredentials(@AuthenticationPrincipal UserDetails userDetails) {
        return authService.validCredentials(userDetails);
    }
}