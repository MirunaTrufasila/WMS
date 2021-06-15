package com.lts.repository;

import com.lts.model.entities.Furnizor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FurnizoriRepository extends JpaRepository<Furnizor, Long> {

    Page<Furnizor> getByNumeFirmaContainsIgnoreCase(String numeFirma, Pageable pageable);

}