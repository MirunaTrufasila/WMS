package com.lts.util;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Set;

/**
 * Thumbs up for https://www.manongdao.com/q-308133.html !!!
 */
public class JpaUtil {

    public static <T> long count(final EntityManager entityManager,
                                 final CriteriaBuilder cb,
                                 final CriteriaQuery<T> criteria,
                                 Root<T> root) {
        CriteriaQuery<Long> query = createCountQuery(cb, criteria, root);
        return entityManager.createQuery(query).getSingleResult();
    }

    private static <T> CriteriaQuery<Long> createCountQuery(final CriteriaBuilder cb,
                                                            final CriteriaQuery<T> criteria,
                                                            final Root<T> root) {

        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        final Root<T> countRoot = countQuery.from(criteria.getResultType());

        doJoins(root.getJoins(), countRoot);
        doJoinsOnFetches(root.getFetches(), countRoot);

        countQuery.select(cb.count(countRoot));
        countQuery.where(criteria.getRestriction());

        countRoot.alias(root.getAlias());

        return countQuery.distinct(criteria.isDistinct());
    }

    @SuppressWarnings("unchecked")
    private static void doJoinsOnFetches(Set<? extends Fetch<?, ?>> joins, Root<?> root) {
        doJoins((Set<? extends Join<?, ?>>) joins, root);
    }

    private static void doJoins(Set<? extends Join<?, ?>> joins, Root<?> root) {
        for (Join<?, ?> join : joins) {
            Join<?, ?> joined = root.join(join.getAttribute().getName(), join.getJoinType());
            joined.alias(join.getAlias());
            doJoins(join.getJoins(), joined);
        }
    }

    private static void doJoins(Set<? extends Join<?, ?>> joins, Join<?, ?> root) {
        for (Join<?, ?> join : joins) {
            Join<?, ?> joined = root.join(join.getAttribute().getName(), join.getJoinType());
            joined.alias(join.getAlias());
            doJoins(join.getJoins(), joined);
        }
    }
}
