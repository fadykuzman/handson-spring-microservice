package learn.fady.util.http;

import org.springframework.http.HttpStatus;

public class HttpErrorInfo {
    private final HttpStatus httpStatus;
    private final String path;
    private final String message;

    public HttpErrorInfo(HttpStatus httpStatus, String path, String message) {
        this.httpStatus = httpStatus;
        this.path = path;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }
}
