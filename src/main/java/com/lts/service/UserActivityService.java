package com.lts.service;

import com.lts.model.entities.UserActivity;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserActivityService {

    Page<UserActivity> filter(HttpServletRequest request, Map<String, Object> filters, int pageNumber, int pageSize);

    UserActivity getUserActivity(Long id);
}