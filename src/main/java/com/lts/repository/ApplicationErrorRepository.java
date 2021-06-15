package com.lts.repository;

import com.lts.model.entities.ApplicationError;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ApplicationErrorRepository extends JpaRepository<ApplicationError, Long> {

    int deleteAllByCreatedAtBefore(LocalDateTime maxDate);
}