package com.lts.controller.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;

public class RequestErrorDetails {

    private String exceptionMessage;
    private String exceptionRootCause;
    private String method;
    private String requestURL;
    private String requestURI;
    private String servletPath;
    private String serverName;
    private String schema;
    private String query;
    private String contentType;
    private String characterEncoding;
    private int contentLength;
    private String locale;
    private String authenticatedUser;

    private String remoteHost;
    private String remoteAddress;
    private int remotePort;

    public RequestErrorDetails(HttpServletRequest request, Exception exc) {
        this.exceptionMessage = exc.getMessage();
        this.exceptionRootCause = ExceptionUtils.getRootCauseMessage(exc);
        this.method = request.getMethod();
        if (request.getUserPrincipal() != null) {
            this.authenticatedUser = request.getUserPrincipal().getName();
        }
        this.servletPath = request.getServletPath();
        this.serverName = request.getServerName();
        this.schema = request.getScheme();
        this.query = request.getQueryString();
        this.contentType = request.getContentType();
        this.characterEncoding = request.getCharacterEncoding();
        this.contentLength = request.getContentLength();
        this.locale = request.getLocale().getLanguage();
        this.remoteHost = request.getRemoteHost();
        this.remoteAddress = request.getRemoteAddr();
        this.remotePort = request.getRemotePort();
        this.requestURI = request.getRequestURI();
        this.requestURL = request.getRequestURL().toString();
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public String getExceptionRootCause() {
        return exceptionRootCause;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getServletPath() {
        return servletPath;
    }

    public String getServerName() {
        return serverName;
    }

    public String getSchema() {
        return schema;
    }

    public String getQuery() {
        return query;
    }

    public String getContentType() {
        return contentType;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getLocale() {
        return locale;
    }

    public String getAuthenticatedUser() {
        return authenticatedUser;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public int getRemotePort() {
        return remotePort;
    }

    @Override
    public String toString() {
        return "\n\t\texceptionMessage='" + exceptionMessage + '\'' + '\n' +
                "\t\texceptionRootCause='" + exceptionRootCause + '\'' + '\n' +
                "\t\tmethod='" + method + '\'' + '\n' +
                "\t\tauthenticatedUser='" + authenticatedUser + '\'' + '\n' +
                "\t\trequestURL='" + requestURL + '\'' + '\n' +
                "\t\trequestURI='" + requestURI + '\'' + '\n' +
                "\t\tservletPath='" + servletPath + '\'' + '\n' +
                "\t\tserverName='" + serverName + '\'' + '\n' +
                "\t\tschema='" + schema + '\'' + '\n' +
                "\t\tquery='" + query + '\'' + '\n' +
                "\t\tcontentType='" + contentType + '\'' + '\n' +
                "\t\tcharacterEncoding='" + characterEncoding + '\'' + '\n' +
                "\t\tcontentLength=" + contentLength + '\'' + '\n' +
                "\t\tlocale='" + locale + '\'' + '\n' +
                "\t\tremoteHost='" + remoteHost + '\'' + '\n' +
                "\t\tremoteAddress='" + remoteAddress + '\'' + '\n' +
                "\t\tremotePort=" + remotePort;
    }
}
