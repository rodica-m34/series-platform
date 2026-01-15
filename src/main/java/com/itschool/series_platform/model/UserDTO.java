package com.itschool.series_platform.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itschool.series_platform.exception.InvalidInputException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

public record UserDTO (@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                        Long id,
                        String name,
                        String email,
                        Integer age,
                        String password,
                        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                        List<SeriesDTO> series) {

    @Override
    public Integer age() {
        if (age < 0) {
            throw new InvalidInputException("Age cannot be negative!");
        }
        if (age < 12) {
            throw new InvalidInputException("You are not allowed to directly create an account! An adult has to create your account");
        }
        return age;
    }

    @Override
    public String email() {
        boolean valid = EmailValidator.getInstance().isValid(email);
        if (!valid) {
            throw new InvalidInputException("Invalid email address!");
        }
        return email;
    }
}
