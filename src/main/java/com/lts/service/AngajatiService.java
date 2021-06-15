package com.lts.service;

import com.lts.model.entities.Angajat;
import org.springframework.data.domain.Page;

public interface AngajatiService {

    Page<Angajat> filter(String filter, String filter1, Object value, int pageNumber, int pageSize);

    Page<Angajat> get(int pageNumber, int pageSize);

    Angajat create(Angajat angajat);

    Angajat update(Angajat angajat);

    void delete(Long id);

    Angajat getAngajat(Long id);

}
