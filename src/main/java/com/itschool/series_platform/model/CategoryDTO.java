package com.itschool.series_platform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryDTO (
                           Long id,
                           @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                           String name){
}
