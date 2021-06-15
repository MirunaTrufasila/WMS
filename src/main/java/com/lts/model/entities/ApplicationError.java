package com.lts.model.entities;

import com.lts.model.Auditable;
import com.lts.model.types.ErrorLevel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "application_error")
public class ApplicationError extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    private int origin;

    private int app;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ERROR', 'WARNING', 'INFO')")
    private ErrorLevel level;

    private String email;

    @Column(name = "fixed", columnDefinition = "BIT default 0")
    private boolean fixed = false;

    @Column(name = "email_sent", columnDefinition = "BIT default 0")
    private boolean emailSent = false;

    private String message;
    @Lob
    private String stacktrace;

    @Lob
    @Column(name = "root_cause")
    private String rootCause;

    private String method;

    @Column(name = "servlet_path")
    private String servletPath;

    @Column(name = "context_path")
    private String contextPath;

    @Column(name = "request_params")
    private String requestParams;

    @Column(name = "request_body")
    @Lob
    private String requestBody;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public ErrorLevel getLevel() {
        return level;
    }

    public void setLevel(ErrorLevel level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }


    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getServletPath() {
        return servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public int getApp() {
        return app;
    }

    public void setApp(int app) {
        this.app = app;
    }

    @Override
    public String toString() {
        return "ApplicationError{" +
                "id=" + id +
                ", origin=" + origin +
                ", app=" + app +
                ", level=" + level +
                ", email='" + email + '\'' +
                ", fixed=" + fixed +
                ", emailSent=" + emailSent +
                ", message='" + message + '\'' +
                ", stacktrace='" + stacktrace + '\'' +
                ", rootCause='" + rootCause + '\'' +
                ", method='" + method + '\'' +
                ", servletPath='" + servletPath + '\'' +
                ", contextPath='" + contextPath + '\'' +
                ", requestParams='" + requestParams + '\'' +
                ", requestBody='" + requestBody + '\'' +
                '}';
    }
}
