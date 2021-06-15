package com.lts.service.impl;

import com.lts.model.entities.CompanyDetails;
import com.lts.repository.CompanyDetailsRepository;
import com.lts.service.CompanyDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyDetailsServiceImpl implements CompanyDetailsService {

    private static final int NR_BANCI = 2;

    private final CompanyDetailsRepository companyDetailsRepository;

    public CompanyDetailsServiceImpl(CompanyDetailsRepository companyDetailsRepository) {
        this.companyDetailsRepository = companyDetailsRepository;
    }

    @Override
    public Map<String, String> getCompanyDetails() {
        Iterable<CompanyDetails> it = companyDetailsRepository.findAll();
        final Map<String, String> result = new HashMap<>();
        it.forEach(companyDetails -> result.put(companyDetails.getDenumire(), companyDetails.getValoare()));
        result.put("NR_BANCI", String.valueOf(NR_BANCI));
        return result;
    }

    @Override
    public Map<String, String> saveCompanyDetails(Map<String, String> companyDetails, UserDetails userDetails) {
        List<CompanyDetails> updatedDetails = new ArrayList<>();
        companyDetails.remove("NR_BANCI"); // we dont store this config, used as dto only for now!
        for (Map.Entry<String, String> entry : companyDetails.entrySet()) {
            updatedDetails.add(new CompanyDetails(entry.getKey(), entry.getValue()));
        }
        companyDetailsRepository.deleteAll();
        companyDetailsRepository.saveAll(updatedDetails);
        return getCompanyDetails();
    }
}
