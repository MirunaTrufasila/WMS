package com.lts.controller.advice;

import api.XrayApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lts.controller.api.ValidationErrorPayload;
import com.lts.controller.exception.RequestErrorDetails;
import com.lts.controller.exception.ValidationException;
import com.lts.model.types.Origin;
import com.lts.service.ApplicationErrorService;
import com.lts.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final static Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    private final MessageService messageService;
    private final ApplicationErrorService applicationErrorService;

    public GlobalControllerAdvice(MessageService messageService,
                                  ApplicationErrorService applicationErrorService) {
        this.messageService = messageService;
        this.applicationErrorService = applicationErrorService;
    }

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletRequest req, Exception exception) throws Exception {
        logger.error(messageService.getMessage("logRequestRaised", req.getRequestURI(), exception));
        logger.error(new RequestErrorDetails(req, exception).toString());
        applicationErrorService.create(req, exception, Origin.LTS.getId());
        throw exception;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleAccessDenied(HttpServletRequest req, AccessDeniedException exception) throws Exception {
        logger.error(messageService.getMessage("logRequestRaisedAccDenied", req.getRequestURI()));
        applicationErrorService.create(req, exception, Origin.LTS.getId());
        ObjectMapper mapper = new ObjectMapper();
        ValidationErrorPayload model = new ValidationErrorPayload(exception.getMessage(), messageService.getMessage("errAccessDenied"));
        return mapper.writeValueAsString(model);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ValidationErrorPayload handleValidationError(HttpServletRequest req, ValidationException exception) {
        logger.error(exception.getMessage());
        applicationErrorService.create(req, exception, Origin.LTS.getId());
        return new ValidationErrorPayload<>(exception.getData(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(HttpServletRequest req, MethodArgumentNotValidException exception) throws Exception {
        logger.error("Request: " + req.getRequestURI() + " raised " + exception);
        logger.debug(new RequestErrorDetails(req, exception).toString());
        applicationErrorService.create(req, exception, Origin.LTS.getId());

        List<String> errors = exception.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).collect(Collectors.toList());

        XrayApiError err = new XrayApiError(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage(), errors);

        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
