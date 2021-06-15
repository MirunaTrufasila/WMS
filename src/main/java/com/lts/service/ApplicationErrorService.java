package com.lts.service;

import com.lts.model.entities.ApplicationError;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ApplicationErrorService {

    Page<ApplicationError> filter(Map<String, Object> filters, int pageNumber, int pageSize);

    void create(HttpServletRequest request, Exception exception, int origin);

    ApplicationError update(ApplicationError applicationError, UserDetails userDetails);

    void delete(Long id, UserDetails userDetails);

    ApplicationError getApplicationError(Long id);

}