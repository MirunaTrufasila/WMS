package com.lts.controller;

import com.lts.model.entities.ApplicationError;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lts.service.ApplicationErrorService;

import java.util.Map;

@Api(tags = "Application error")
@RestController
@RequestMapping(path = "/api/v1/errors")
public class ApplicationErrorController {

    private final ApplicationErrorService applicationErrorService;

    public ApplicationErrorController(ApplicationErrorService applicationErrorService) {
        this.applicationErrorService = applicationErrorService;
    }

    @GetMapping("/{id}")
    public ApplicationError getOne(@PathVariable Long id) {
        return applicationErrorService.getApplicationError(id);
    }


    @PostMapping("/filter")
    public Page<ApplicationError> filter(
            @RequestBody Map<String, Object> filters,
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {
        return applicationErrorService.filter(filters, pageNumber, pageSize);
    }

    @PostMapping("/update")
    public ApplicationError update(@RequestBody ApplicationError applicationError,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        return applicationErrorService.update(applicationError, userDetails);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id,
                       @AuthenticationPrincipal UserDetails userDetails) {
        applicationErrorService.delete(id, userDetails);
    }
}