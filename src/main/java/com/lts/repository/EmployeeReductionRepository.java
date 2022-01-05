package com.lts.repository;

import com.lts.model.entities.EmployeeReduction;
import com.lts.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeReductionRepository extends JpaRepository<EmployeeReduction, Long> {

    Page<EmployeeReduction> getByFileNameContainsIgnoreCase(Pageable page, String name);

}