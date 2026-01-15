package com.itschool.series_platform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// HTTP 400 status
public class InvalidInputException extends ResponseStatusException {
    public InvalidInputException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}

