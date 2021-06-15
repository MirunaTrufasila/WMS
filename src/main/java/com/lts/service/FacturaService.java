package com.lts.service;

import com.lts.model.entities.Factura;
import org.springframework.data.domain.Page;

public interface FacturaService {

    Page<Factura> filter(String filter, Object value, int pageNumber, int pageSize);

    Page<Factura> get(int pageNumber, int pageSize);

    Factura create(Factura factura);

    Factura update(Factura factura);

    void delete(Long id);

    Factura getFactura(Long id);

}
