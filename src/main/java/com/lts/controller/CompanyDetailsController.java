package com.lts.controller;

import io.swagger.annotations.Api;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lts.service.CompanyDetailsService;

import java.util.Map;

@Api(tags = "Company details")
@RestController
@RequestMapping(path = "/api/v1/company")
public class CompanyDetailsController {

    private final CompanyDetailsService companyDetailsService;

    public CompanyDetailsController(CompanyDetailsService companyDetailsService) {
        this.companyDetailsService = companyDetailsService;
    }

    @GetMapping
    public Map<String, String> getCompanyDetails() {
        return companyDetailsService.getCompanyDetails();
    }

    @PostMapping
    public Map<String, String> update(@RequestBody Map<String, String> details,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        return companyDetailsService.saveCompanyDetails(details, userDetails);
    }
}