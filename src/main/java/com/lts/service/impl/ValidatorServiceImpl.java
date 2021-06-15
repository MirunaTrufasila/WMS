package com.lts.service.impl;

import com.lts.service.MessageService;
import com.lts.service.ValidatorService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidatorServiceImpl implements ValidatorService {

    private final static Logger logger = LoggerFactory.getLogger(ValidatorServiceImpl.class);
    private final MessageService messageService;

    public ValidatorServiceImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public boolean validateEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    @Override
    public boolean validateEmails(String emails) {
        if (StringUtils.isEmpty(emails)) {
            return false;
        }
        String[] emailAddresses;
        if (emails.contains(";")) {
            emailAddresses = emails.split(";");
        } else {
            emailAddresses = emails.split(",");
        }
        for (String emailAddress : emailAddresses) {
            if (!validateEmail(emailAddress)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isPhoneNumberValid(String phoneNumber) {
        try {
            Pattern regexPattern = Pattern.compile("^\\+?[0-9]{1}[0-9\\-\\. \\(\\)]{0,25}$");
            Matcher regMatcher = regexPattern.matcher(phoneNumber);
            return regMatcher.matches();
        } catch (Exception exc) {
            logger.error(messageService.getMessage("logInvalidPhone", phoneNumber));
        }

        return false;
    }
}
