package com.lts.repository;

import com.lts.model.entities.Angajat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AngajatiRepository extends JpaRepository<Angajat, Long> {

    Page<Angajat> getByFirstNameContainsIgnoreCase(String firstName, Pageable pageable);

    Page<Angajat> getByLastNameContainsIgnoreCase(String lastName, Pageable pageable);

}