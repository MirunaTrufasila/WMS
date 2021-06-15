package com.lts.service.impl;

import com.lts.controller.exception.ValidationException;
import com.lts.model.entities.UserActivity;
import com.lts.model.filters.UserActivityFilter;
import com.lts.repository.UserActivityRepository;
import com.lts.service.MessageService;
import com.lts.service.UserActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserActivityServiceImpl implements UserActivityService {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(UserActivityServiceImpl.class);

    private final UserActivityRepository userActivityRepository;
    private final MessageService messageService;
    private final EntityManager entityManager;

    public UserActivityServiceImpl(UserActivityRepository userActivityRepository,
                                   MessageService messageService,
                                   EntityManager entityManager) {
        this.userActivityRepository = userActivityRepository;
        this.messageService = messageService;
        this.entityManager = entityManager;
    }

    // see https://www.baeldung.com/jpa-pagination
    @Override
    public Page<UserActivity> filter(HttpServletRequest request, Map<String, Object> filters, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber - 1, pageSize);
        if (filters == null) {
            filters = new HashMap<>();
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserActivity> criteriaQuery = criteriaBuilder
                .createQuery(UserActivity.class);
        Root<UserActivity> root = criteriaQuery.from(UserActivity.class);
        CriteriaQuery<UserActivity> select = criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("contextPath"), request.getContextPath()));

        Object filter = filters.get(UserActivityFilter.USERNAME.name());
        if (!ObjectUtils.isEmpty(filter)) {
            predicates.add(criteriaBuilder.like(root.get("username"), "%" + filter + "%"));
        }

        filter = filters.get(UserActivityFilter.SERVLET_PATH.name());
        if (!ObjectUtils.isEmpty(filter)) {
            predicates.add(criteriaBuilder.like(root.get("servletPath"), "%" + filter + "%"));
        }

        filter = filters.get(UserActivityFilter.METHOD.name());
        if (filter instanceof List && !((List) filter).isEmpty()) {
            predicates.add(root.get("method").in(((List<?>) filter).toArray()));
        }

        filter = filters.get(UserActivityFilter.DATA_MIN.name());
        if (!ObjectUtils.isEmpty(filter)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), LocalDateTime.parse(filter.toString(), dtf)));
        }

        filter = filters.get(UserActivityFilter.DATA_MAX.name());
        if (!ObjectUtils.isEmpty(filter)) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), LocalDateTime.parse(filter.toString(), dtf)));
        }

        filter = filters.get(UserActivityFilter.ID.name());
        if (!ObjectUtils.isEmpty(filter)) {
            try {
                predicates.add(criteriaBuilder.equal(root.get("id"), Integer.parseInt(filter.toString())));
            } catch (Exception exc) {
                predicates.add(criteriaBuilder.equal(root.get("id"), -1));
            }
        }

        select.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        select.orderBy(criteriaBuilder.desc(root.get("createdAt")));

        TypedQuery<UserActivity> typedQuery = entityManager.createQuery(select);

        typedQuery.setFirstResult((int) page.getOffset());
        typedQuery.setMaxResults(page.getPageSize());

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(
                countQuery.from(UserActivity.class))).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(typedQuery.getResultList(), page, count);
    }

    @Override
    public UserActivity getUserActivity(Long id) {
        UserActivity container = userActivityRepository.findById(id).orElse(null);
        if (container == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", id));
        }
        return container;
    }
}
