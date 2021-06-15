package com.lts.service.impl;

import com.lts.controller.exception.ValidationException;
import com.lts.model.entities.ApplicationError;
import com.lts.model.filters.ApplicationErrorFilter;
import com.lts.model.types.Application;
import com.lts.model.types.ErrorLevel;
import com.lts.repository.ApplicationErrorRepository;
import com.lts.service.AdminService;
import com.lts.service.ApplicationErrorService;
import com.lts.service.MessageService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationErrorServiceImpl implements ApplicationErrorService {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ApplicationErrorRepository applicationErrorRepository;
    private final MessageService messageService;
    private final EntityManager entityManager;
    private final AdminService adminService;

    public ApplicationErrorServiceImpl(ApplicationErrorRepository applicationErrorRepository,
                                       MessageService messageService,
                                       EntityManager entityManager,
                                       AdminService adminService) {
        this.applicationErrorRepository = applicationErrorRepository;
        this.messageService = messageService;
        this.entityManager = entityManager;
        this.adminService = adminService;
    }

    // see https://www.baeldung.com/jpa-pagination
    @Override
    public Page<ApplicationError> filter(Map<String, Object> filters, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber - 1, pageSize);
        if (filters == null) {
            filters = new HashMap<>();
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // actual query
        CriteriaQuery<ApplicationError> criteriaQuery = criteriaBuilder.createQuery(ApplicationError.class);
        Root<ApplicationError> root = criteriaQuery.from(ApplicationError.class);
        CriteriaQuery<ApplicationError> select = criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();

        Object filter = filters.get(ApplicationErrorFilter.USERNAME.name());
        if (!ObjectUtils.isEmpty(filter)) {
            predicates.add(criteriaBuilder.like(root.get("createdBy"), "%" + filter + "%"));
        }

        filter = filters.get(ApplicationErrorFilter.DATA_MIN.name());
        if (!ObjectUtils.isEmpty(filter)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), LocalDateTime.parse(filter.toString(), dtf)));
        }

        filter = filters.get(ApplicationErrorFilter.DATA_MAX.name());
        if (!ObjectUtils.isEmpty(filter)) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), LocalDateTime.parse(filter.toString(), dtf)));
        }

        filter = filters.get(ApplicationErrorFilter.FIXED.name());
        if (filter instanceof List && !((List) filter).isEmpty()) {
            predicates.add(root.get("fixed").in(((List<?>) filter).toArray()));
        }

        filter = filters.get(ApplicationErrorFilter.EMAIL_SENT.name());
        if (filter instanceof List && !((List) filter).isEmpty()) {
            predicates.add(root.get("emailSent").in(((List<?>) filter).toArray()));
        }

        filter = filters.get(ApplicationErrorFilter.LEVEL.name());
        if (filter instanceof List && !((List) filter).isEmpty()) {
            List<String> levelsFilter = (List<String>) filter;
            ErrorLevel[] levels = new ErrorLevel[levelsFilter.size()];
            for (int i = 0; i < levelsFilter.size(); i++) {
                levels[i] = ErrorLevel.valueOf(levelsFilter.get(i));
            }
            predicates.add(root.get("level").in(levels));
        }

        filter = filters.get(ApplicationErrorFilter.ORIGIN.name());
        if (filter instanceof List && !((List) filter).isEmpty()) {
            predicates.add(root.get("origin").in(((List<?>) filter).toArray()));
        }

        filter = filters.get(ApplicationErrorFilter.APPLICATION.name());
        if (filter instanceof List && !((List) filter).isEmpty()) {
            predicates.add(root.get("app").in(((List<?>) filter).toArray()));
        }

        filter = filters.get(ApplicationErrorFilter.MESSAGE.name());
        if (!ObjectUtils.isEmpty(filter)) {
            predicates.add(criteriaBuilder.like(root.get("message"), "%" + filter + "%"));
        }

        filter = filters.get(ApplicationErrorFilter.ID.name());
        if (!ObjectUtils.isEmpty(filter)) {
            try {
                predicates.add(criteriaBuilder.equal(root.get("id"), Integer.parseInt(filter.toString())));
            } catch (Exception exc) {
                // enter junk in the filter and junk is what you'll receive!
                predicates.add(criteriaBuilder.equal(root.get("id"), -1));
            }
        }

        select.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        select.orderBy(criteriaBuilder.desc(root.get("createdAt")));

        TypedQuery<ApplicationError> typedQuery = entityManager.createQuery(select);

        typedQuery.setFirstResult((int) page.getOffset());
        typedQuery.setMaxResults(page.getPageSize());

        // count query for pagination
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(
                countQuery.from(ApplicationError.class))).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(typedQuery.getResultList(), page, count);
    }

    @Override
    public ApplicationError update(ApplicationError applicationError, UserDetails userDetails) {
        validate(applicationError);

        ApplicationError temp = applicationErrorRepository.findById(applicationError.getId()).orElse(null);
        if (temp == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", applicationError.getId()));
        }
        return applicationErrorRepository.save(applicationError);
    }

    private void validate(ApplicationError applicationError) {
        if (StringUtils.isEmpty(applicationError.getMessage())) {
            throw new ValidationException(messageService.getMessage("errNameIsEmpty"));
        }
    }

    @Override
    public void delete(Long id, UserDetails userDetails) {
        ApplicationError temp = applicationErrorRepository.findById(id).orElse(null);
        if (temp == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", id));
        }
        applicationErrorRepository.delete(temp);
    }

    @Override
    public ApplicationError getApplicationError(Long id) {
        ApplicationError applicationError = applicationErrorRepository.findById(id).orElse(null);
        if (applicationError == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", id));
        }
        return applicationError;
    }


    @Override
    public void create(HttpServletRequest request, Exception exception, int origin) {
        if (adminService.isTestProfile()) {
            return;
        }
        ApplicationError error = new ApplicationError();
        if (exception instanceof ValidationException) {
            error.setLevel(ErrorLevel.WARNING);
        } else {
            error.setLevel(ErrorLevel.ERROR);
        }
        error.setMessage(exception.getMessage());
        error.setRootCause(ExceptionUtils.getRootCauseMessage(exception));
        error.setStacktrace(ExceptionUtils.getStackTrace(exception));
        error.setOrigin(origin);
        if (request != null) {
            error.setServletPath(request.getServletPath());
            error.setMethod(request.getMethod());
            error.setContextPath(request.getContextPath());
            String requestBody = null;
            try {
                if (request instanceof ContentCachingRequestWrapper) {
                    byte[] bodyContent = ((ContentCachingRequestWrapper) request).getContentAsByteArray();
                    if (bodyContent != null) {
                        requestBody = IOUtils.toString(bodyContent, "UTF-8");
                    }
                } else {
                    if (request.getReader() != null) {
                        requestBody = IOUtils.toString(request.getReader());
                    }
                }
            } catch (IOException ioex) {
                // do nothing
            }
            error.setRequestBody(requestBody);
            error.setRequestParams(request.getQueryString());
            for (Application app : Application.values()) {
                if (StringUtils.containsIgnoreCase(error.getContextPath(), app.getContextPath())) {
                    error.setApp(app.getId());
                    // not breaking on purpose, to allow override of the "NONE" value with actual application id!
                }
            }
        }
        applicationErrorRepository.save(error);
    }

}
