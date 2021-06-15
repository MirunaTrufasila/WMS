package com.lts.controller.exception;


import com.lts.service.MessageService;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String type, long id, MessageService messageService) {
        super(messageService.getMessage("errRecordNotFound", type, id));
    }

    public RecordNotFoundException(String type, String identifier, MessageService messageService) {
        super(messageService.getMessage("errRecordNotFound", type, identifier));
    }
}
