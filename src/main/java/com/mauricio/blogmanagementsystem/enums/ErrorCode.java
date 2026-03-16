package com.mauricio.blogmanagementsystem.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NOT_FOUND("Not Found", HttpStatus.NOT_FOUND),
    BAD_REQUEST("Bad Request", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR("Validation Error", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("Unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("Forbidden", HttpStatus.FORBIDDEN),
    CONFLICT("Conflict", HttpStatus.CONFLICT),
    INTERNAL_SERVER_ERROR("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getStatus() {
        return httpStatus.value();
    }
}

