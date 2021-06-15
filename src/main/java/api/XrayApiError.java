package api;

import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;

public class XrayApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public XrayApiError() {
    }

    public XrayApiError(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public XrayApiError(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonList(error);
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
