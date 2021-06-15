package com.lts.repository;

import com.lts.model.entities.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Page<Factura> getByNrFacturaContainsIgnoreCase(String nrFactura, Pageable pageable);

}