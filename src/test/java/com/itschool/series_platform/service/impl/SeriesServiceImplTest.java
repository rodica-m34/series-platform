package com.itschool.series_platform.service.impl;

import com.itschool.series_platform.entity.CategoryType;
import com.itschool.series_platform.entity.Series;
import com.itschool.series_platform.model.SeriesDTO;
import com.itschool.series_platform.repository.SeriesRepository;
import com.itschool.series_platform.service.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SeriesServiceImplTest {

    private static final long ID_SERIES = 1L;
    private static final String NAME = "fake name";
    private static final Integer NO_OF_SEASONS = 123;
    private static final CategoryType CATEGORY_TYPE = CategoryType.COMEDY;

    @Mock
    private SeriesRepository seriesRepository;

    private SeriesService seriesService;

    @BeforeEach
    void setUp() {
        seriesService = new SeriesServiceImpl(seriesRepository);
    }

    @Test
    void createSeries() {
        SeriesDTO seriesDTO = new SeriesDTO(null, NAME, NO_OF_SEASONS, CATEGORY_TYPE);

        Series seriesEntity = new Series(NAME, NO_OF_SEASONS, CATEGORY_TYPE);
        seriesEntity.setId(ID_SERIES);
        Mockito.when(seriesRepository.save(
                Mockito.argThat( // any Series object that matches the following conditions
                        series -> series.getName().equals(NAME)
                                && series.getNoOfSeasons().equals(NO_OF_SEASONS))
                )
        ).thenReturn(seriesEntity);

        //the method to be tested, expected to return a SeriesDTO
        SeriesDTO createdSeries = seriesService.createSeries(seriesDTO);

        //verify the result
        assertEquals(NAME, createdSeries.name());
        assertEquals(NO_OF_SEASONS, createdSeries.noOfSeasons());
        assertEquals(ID_SERIES, createdSeries.id());
        assertEquals(CATEGORY_TYPE, createdSeries.categoryType());
    }

    @Test
    void findSeriesByName() {
        Series seriesEntity = new Series(NAME, NO_OF_SEASONS, CATEGORY_TYPE);
        seriesEntity.setId(ID_SERIES);
        Mockito.when(seriesRepository.findSeriesByNameContainingIgnoreCase(NAME))
                .thenReturn(List.of(seriesEntity));

        //the method to be tested
        List<SeriesDTO> seriesDTOS = seriesService.findSeriesByName(NAME);

        SeriesDTO seriesDTO = seriesDTOS.getFirst();

        assertEquals(1, seriesDTOS.size());
        assertEquals(NAME, seriesDTO.name());
        assertEquals(NO_OF_SEASONS, seriesDTO.noOfSeasons());
        assertEquals(ID_SERIES, seriesDTO.id());
        assertEquals(CATEGORY_TYPE, seriesDTO.categoryType());
    }
}
