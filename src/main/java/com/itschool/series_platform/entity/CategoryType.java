package com.itschool.series_platform.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.itschool.series_platform.exception.CategoryNotFoundException;

public enum CategoryType {
    COMEDY,
    DRAMA,
    THRILLER,
    SF,
    CHILDREN_SERIES;

    @JsonCreator
    public static CategoryType from(String value) {
            try {
                return CategoryType.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new CategoryNotFoundException(
                        "Invalid category. Allowed values: COMEDY, DRAMA, THRILLER, SF, CHILDREN_SERIES"
                );
            }
    }
}
