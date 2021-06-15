package com.lts.service;

import com.lts.model.entities.Furnizor;
import org.springframework.data.domain.Page;

public interface FurnizoriService {

    Page<Furnizor> filter(String filter, Object value, int pageNumber, int pageSize);

    Page<Furnizor> get(int pageNumber, int pageSize);

    Furnizor create(Furnizor furnizor);

    Furnizor update(Furnizor furnizor);

    void delete(Long id);

    Furnizor getFurnizor(Long id);

}
