package com.lts.service.impl;

import com.lts.controller.exception.ValidationException;
import com.lts.model.entities.Factura;
import com.lts.model.filters.FacturaFilter;
import com.lts.repository.FacturaRepository;
import com.lts.service.FacturaService;
import com.lts.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaServiceImpl implements FacturaService {

    private static final Logger logger = LoggerFactory.getLogger(FacturaServiceImpl.class);

    private final FacturaRepository facturaRepository;
    private final MessageService messageService;

    public FacturaServiceImpl(FacturaRepository facturaRepository,
                              MessageService messageService) {
        this.facturaRepository = facturaRepository;
        this.messageService = messageService;
    }

    @Override
    public Page<Factura> filter(String filter, Object value, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber - 1, pageSize);
        Page<Factura> facturaPage;
        if (StringUtils.equals(filter, FacturaFilter.NR_FACTURA.name())
                && value != null && !value.toString().isEmpty()) {
            facturaPage = facturaRepository.getByNrFacturaContainsIgnoreCase(value.toString(), page);
        } else {
            facturaPage = facturaRepository.findAll(page);
        }
        return facturaPage;
    }


    @Override
    public Page<Factura> get(int pageNumber, int pageSize) {
        return filter(null, null, pageNumber, pageSize);
    }

    @Override
    @Transactional
    public Factura create(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    @Transactional
    public Factura update(Factura factura) {
        if (factura.getId() == 0) {
            throw new ValidationException(factura, messageService.getMessage("errIdNotSet"));
        }

        return facturaRepository.save(factura);
    }

    @Override
    public void delete(Long facturaId) {
        Factura factura = facturaRepository.findById(facturaId).orElse(null);
        if (factura == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", facturaId));
        }
        facturaRepository.delete(factura);
    }

    @Override
    public Factura getFactura(Long id) {
        Factura factura = facturaRepository.findById(id).orElse(null);
        if (factura == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", id));
        }
        return factura;
    }
}
