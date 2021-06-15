package com.lts.repository;

import com.lts.model.entities.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

    void deleteAllByCreatedAtBefore(LocalDateTime maxDate);

    int countByCreatedAtBefore(LocalDateTime maxDate);
}
