package com.lts.service.impl;

import com.lts.controller.exception.ValidationException;
import com.lts.model.entities.Furnizor;
import com.lts.model.filters.FurnizorFilter;
import com.lts.repository.FurnizoriRepository;
import com.lts.service.FurnizoriService;
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
public class FurnizoriServiceImpl implements FurnizoriService {

    private static final Logger logger = LoggerFactory.getLogger(FurnizoriServiceImpl.class);

    private final FurnizoriRepository furnizoriRepository;
    private final MessageService messageService;

    public FurnizoriServiceImpl(FurnizoriRepository furnizoriRepository,
                                MessageService messageService) {
        this.furnizoriRepository = furnizoriRepository;
        this.messageService = messageService;
    }

    @Override
    public Page<Furnizor> filter(String filter, Object value, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber - 1, pageSize);
        Page<Furnizor> furnizorPage;
        if (StringUtils.equals(filter, FurnizorFilter.NUME_FIRMA.name())
                && value != null && !value.toString().isEmpty()) {
            furnizorPage = furnizoriRepository.getByNumeFirmaContainsIgnoreCase(value.toString(), page);
        } else {
            furnizorPage = furnizoriRepository.findAll(page);
        }
        return furnizorPage;
    }


    @Override
    public Page<Furnizor> get(int pageNumber, int pageSize) {
        return filter(null, null, pageNumber, pageSize);
    }

    @Override
    @Transactional
    public Furnizor create(Furnizor furnizor) {
        return furnizoriRepository.save(furnizor);
    }

    @Override
    @Transactional
    public Furnizor update(Furnizor furnizor) {
        if (furnizor.getId() == 0) {
            throw new ValidationException(furnizor, messageService.getMessage("errIdNotSet"));
        }

        return furnizoriRepository.save(furnizor);
    }

    @Override
    public void delete(Long furnizorId) {
        Furnizor furnizor = furnizoriRepository.findById(furnizorId).orElse(null);
        if (furnizor == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", furnizorId));
        }
        furnizoriRepository.delete(furnizor);
    }

    @Override
    public Furnizor getFurnizor(Long id) {
        Furnizor furnizor = furnizoriRepository.findById(id).orElse(null);
        if (furnizor == null) {
            throw new ValidationException(messageService.getMessage("errRecordWithIdNotFound", id));
        }
        return furnizor;
    }
}
