package com.itschool.series_platform.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itschool.series_platform.exception.InvalidInputException;

public record SeriesDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                        Long id,
                        String name,
                        Integer noOfSeasons,
                        CategoryDTO category) {
    @Override
    public Integer noOfSeasons() {
        if (noOfSeasons == null || noOfSeasons <= 0) {
            throw new InvalidInputException("No of seasons must be greater than zero!");
        }
        return noOfSeasons;
    }
}
