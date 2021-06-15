package com.lts.service.impl;

import com.lts.controller.exception.ValidationException;
import com.lts.model.entities.Angajat;
import com.lts.model.filters.AngajatFilter;
import com.lts.repository.AngajatiRepository;
import com.lts.service.AngajatiService;
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
public class AngajatiServiceImpl implements AngajatiService {

    private static final Logger logger = LoggerFactory.getLogger(AngajatiServiceImpl.class);

    private final AngajatiRepository angajatiRepository;
    private final MessageService messageService;

    public AngajatiServiceImpl(AngajatiRepository angajatiRepository,
                               MessageService messageService) {
        this.angajatiRepository = angajatiRepository;
        this.messageService = messageService;
    }

    @Override
    public Page<Angajat> filter(String filter, String filter1, Object value, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber - 1, pageSize);
        Page<Angajat> angajatPage;
        if (StringUtils.equals(filter, AngajatFilter.FIRST_NAME.name())
                && value != null && !value.toString().isEmpty()) {
            angajatPage = angajatiRepository.getByFirstNameContainsIgnoreCase(value.toString(), page);
        } else if (StringUtils.equals(filter1, AngajatFilter.LAST_NAME.name())
                && value != null && !value.toString().isEmpty()) {
            angajatPage = angajatiRepository.getByLastNameContainsIgnoreCase(value.toString(), page);
        } else {
            angajatPage = angajatiRepository.findAll(page);
        }
        return angajatPage;
    }


    @Override
    public Page<Angajat> get(int pageNumber, int pageSize) {
        return filter(null, null, null, pageNumber, pageSize);
    }

    @Override
    @Transactional
    public Angajat create(Angajat angajat) {
        return angajatiRepository.save(angajat);
    }

    @Override
    @Transactional
    public Angajat update(Angajat angajat) {
        if (angajat.getId() == 0) {
            throw new ValidationException(angajat, messageService.getMessage("errIdNotSet"));
        }

        return angajatiRepository.save(angajat);
    }

    @Override
    public void delete(Long angajatId) {
        Angajat angajat = angajatiRepository.findById(angajatId).orElse(null);
        if (angajat == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", angajatId));
        }
        angajatiRepository.delete(angajat);
    }

    @Override
    public Angajat getAngajat(Long id) {
        Angajat angajat = angajatiRepository.findById(id).orElse(null);
        if (angajat == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", id));
        }
        return angajat;
    }

}
