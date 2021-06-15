package com.lts.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface CompanyDetailsService {

    Map<String, String> getCompanyDetails();

    Map<String, String> saveCompanyDetails(Map<String, String> companyDetails, UserDetails userDetails);
}
