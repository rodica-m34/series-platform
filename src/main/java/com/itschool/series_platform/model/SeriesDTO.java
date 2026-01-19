package com.itschool.series_platform.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itschool.series_platform.entity.CategoryType;
import com.itschool.series_platform.exception.CategoryNotFoundException;
import com.itschool.series_platform.exception.InvalidInputException;

public record SeriesDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                        Long id,
                        String name,
                        Integer noOfSeasons,
                        CategoryType categoryType) {
    @Override
    public Integer noOfSeasons() {
        if (noOfSeasons == null || noOfSeasons <= 0) {
            throw new InvalidInputException("No of seasons must be greater than zero!");
        }
        return noOfSeasons;
    }
}
