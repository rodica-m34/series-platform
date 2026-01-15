package com.itschool.series_platform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//HTTP 404 status
public class CategoryNotFoundException extends ResponseStatusException {
    public CategoryNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
