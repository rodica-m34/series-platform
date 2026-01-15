package com.itschool.series_platform.service.impl;

import com.itschool.series_platform.entity.Category;
import com.itschool.series_platform.entity.Series;
import com.itschool.series_platform.model.CategoryDTO;
import com.itschool.series_platform.model.SeriesDTO;
import com.itschool.series_platform.repository.CategoryRepository;
import com.itschool.series_platform.repository.SeriesRepository;
import com.itschool.series_platform.service.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SeriesServiceImplTest {

    private static final Long idCategory = 1L;
    private static final Long idSeries = 1L;

    private static final String name = "fake name";
    private static final Integer noOfSeasons = 123;

    @Mock
    private SeriesRepository seriesRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Category category;

    private SeriesService seriesService;

    @BeforeEach
    void setUp() {
        seriesService = new SeriesServiceImpl(seriesRepository, categoryRepository);
    }

    @Test
    void createSeries() {
        // create a dummy SeriesDTO object to be used as an argument for the SeriesServiceImpl::createSeries method
        CategoryDTO categoryDTO = new CategoryDTO(idCategory, null);
        SeriesDTO seriesDTO = new SeriesDTO(null, name, noOfSeasons, categoryDTO);

        Mockito.when(categoryRepository.findById(idCategory)) // when categoryRepository.findById is called with idCategory
                .thenReturn(Optional.of(category)); // then return a mocked category instead of hitting the database; a Category object is needed for creating a Series object

        Series seriesEntity = new Series(name,noOfSeasons);
        seriesEntity.setId(idSeries);
        Mockito.when(seriesRepository.save(
                Mockito.argThat( // any Series object that matches the following conditions
                        series -> series.getName().equals(name) && series.getNoOfSeasons().equals(noOfSeasons))// name & noOfSeasons matches
                )
        ).thenReturn(seriesEntity);

        //the method to be tested, expected to return a SeriesDTO
        SeriesDTO createdSeries = seriesService.createSeries(seriesDTO);

        //verify the result
        assertEquals(name, createdSeries.name()); // ensure the name is the one from database
        assertEquals(noOfSeasons, createdSeries.noOfSeasons()); // ensure the noOfSeasons is the one from database
        assertEquals(idSeries, createdSeries.id()); // check if the created order received the expected ID extracted from database

    }
}