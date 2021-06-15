package com.lts.service;

public interface MessageService {

    /**
     * Gets a message for the specified code. If the message is not found, a "translation missing" string is returned
     *
     * @param code the code
     * @return the resolved message, for current locale (LocaleContextHolder.getLocale())
     */
    String getMessage(String code);

    /**
     * Gets a certain message for the manually specified locale. Used by login form.
     *
     * @param code the message code
     * @param lang the current lang/locale (e.g. fr, de, etc)
     * @return the translated bundle
     */
    String getMessageForLang(String code, String lang);

    /**
     * Gets a certain message for the manually specified locale. Used by login form.
     *
     * @param code the message code
     * @param lang the current lang/locale (e.g. fr, de, etc)
     * @param args the argument list
     * @return the translated bundle
     */
    String getMessageForLang(String code, String lang, Object... args);

    /**
     * Gets a message for the specified code, replacing the message variables with the args content
     *
     * @param code the resource key
     * @param args the parameters
     * @return the message
     */
    String getMessage(String code, Object... args);

}
