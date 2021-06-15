package com.lts.service.impl;

import com.lts.service.MessageService;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private static final Object[] EMPTY_OBJECT = new Object[]{};

    public MessageServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String code) {
        try {
            return messageSource.getMessage(code, EMPTY_OBJECT, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException noSex) {
            return "NO_TRANSLATION_IN_" + LocaleContextHolder.getLocale() + "_FOR_" + code;
        }
    }

    @Override
    public String getMessageForLang(String code, String lang) {
        try {
            return messageSource.getMessage(code, EMPTY_OBJECT, Locale.forLanguageTag(lang));
        } catch (NoSuchMessageException noSex) {
            return "NO_TRANSLATION_IN_" + Locale.forLanguageTag(lang) + "_FOR_" + code;
        }
    }

    @Override
    public String getMessageForLang(String code, String lang, Object... args) {
        try {
            return messageSource.getMessage(code, args, Locale.forLanguageTag(lang));
        } catch (NoSuchMessageException noSex) {
            return "NO_TRANSLATION_IN_" + Locale.forLanguageTag(lang) + "_FOR_" + code;
        }
    }

    @Override
    public String getMessage(String code, Object... args) {
        try {
            return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException noSex) {
            return "NO_TRANSLATION_IN_" + LocaleContextHolder.getLocale() + "_FOR_" + code;
        }
    }
}
