package com.lts.service;

public interface ValidatorService {

    /**
     * Checks whether a single email is valid or not
     *
     * @param email the email to be validated
     * @return the result
     */
    boolean validateEmail(String email);

    /**
     * Checks if a list of emails, separated by , or ; is valid
     *
     * @param emails the emails string
     * @return the result
     */
    boolean validateEmails(String emails);

    /**
     * Draft validation of a phone number
     *
     * @param phone the phone number
     */
    boolean isPhoneNumberValid(String phone);
}
