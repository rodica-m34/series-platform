package com.itschool.series_platform.utils;

import com.itschool.series_platform.entity.Category;
import com.itschool.series_platform.entity.User;
import com.itschool.series_platform.model.CategoryDTO;
import com.itschool.series_platform.model.SeriesDTO;
import com.itschool.series_platform.entity.Series;
import com.itschool.series_platform.model.UserDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ModelConverter {

    private ModelConverter() {}

    public static SeriesDTO toSeriesDTO(Series seriesEntity) {

        Category categoryEntity = seriesEntity.getCategory();
        CategoryDTO categoryDTO = toCategoryDTO (categoryEntity);

        return Optional.ofNullable(seriesEntity)
                .map(series -> new SeriesDTO(series.getId(), series.getName(), series.getNoOfSeasons(), categoryDTO))
                .orElse(null);
    }

    public static Series toSeriesEntity(SeriesDTO  seriesDTO) {
        CategoryDTO categoryDTO = seriesDTO.category();

        return Optional.ofNullable(seriesDTO)
                .map(series -> new Series(series.name(), series.noOfSeasons()))
                .orElse(null);
    }

    private static CategoryDTO toCategoryDTO(Category categoryEntity) {
        return Optional.ofNullable(categoryEntity)
                .map(category -> new CategoryDTO(category.getId(), category.getName()))
                .orElse(null);
    }

    private static Category toCategoryEntity(CategoryDTO categoryDTO) {
        return Optional.ofNullable(categoryDTO)
                .map(category -> new Category( category.name()))
                .orElse(null);

    }

    public static UserDTO toUserDTO(User userEntity) {
        List<Series> seriesEntities = userEntity.getSeries();

        List<SeriesDTO> seriesDTOs = Collections.emptyList();
        if (seriesEntities != null) {
            seriesDTOs = seriesEntities.stream()
                    .map(ModelConverter::toSeriesDTO)
                    .toList();
        }

        return new UserDTO(userEntity.getId(), userEntity.getName(), userEntity.getEmail(),
                userEntity.getAge(), userEntity.getPassword(), seriesDTOs);
    }

    public static User toUserEntity(UserDTO userDTO) {

        List<SeriesDTO> seriesDTOS = userDTO.series();

        List<Series> seriesEntities = Optional.ofNullable(seriesDTOS)
                .map(series -> series.stream()
                        .map(ModelConverter::toSeriesEntity)
                        .toList())
                .orElse(Collections.emptyList());

        return new User(userDTO.name(), userDTO.email(), userDTO.age(), userDTO.password(), seriesEntities);
    }
}

