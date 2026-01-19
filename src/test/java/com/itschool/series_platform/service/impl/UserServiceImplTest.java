package com.itschool.series_platform.service.impl;

import com.itschool.series_platform.entity.CategoryType;
import com.itschool.series_platform.entity.Series;
import com.itschool.series_platform.entity.User;
import com.itschool.series_platform.model.SeriesDTO;
import com.itschool.series_platform.repository.SeriesRepository;
import com.itschool.series_platform.repository.UserRepository;
import com.itschool.series_platform.service.SeriesService;
import com.itschool.series_platform.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final long ID_SERIES = 1L;
    private static final long ID_USER = 1L;
    private static final String NAME = "fake name";
    private static final String EMAIL = "fake email";
    private static final String PASSWORD = "fake password";
    private static final int AGE = 18;

    private static final Integer NO_OF_SEASONS = 123;
    private static final CategoryType CATEGORY_TYPE = CategoryType.COMEDY;

    @Mock
    private SeriesRepository seriesRepository;

    @Mock
    private UserRepository userRepository;

    private SeriesService seriesService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, seriesRepository);
    }

    @Test
    void addToFavoriteList()
    {
        Series seriesEntity = new Series(NAME, NO_OF_SEASONS, CATEGORY_TYPE);
        seriesEntity.setId(ID_SERIES);
        Mockito.when(seriesRepository.findById(ID_SERIES)).thenReturn(Optional.of(seriesEntity));

        List<Series> seriesList = new ArrayList<>(); //assume the list is empty
        User user = new User(NAME, EMAIL, AGE, PASSWORD,seriesList);
        Mockito.when(userRepository.findById(ID_USER)).thenReturn(Optional.of(user));

        Mockito.when(userRepository.save(
                        Mockito.argThat( // any Series object that matches the following conditions
                                userToBeSaved -> userToBeSaved.getName().equals(NAME) && userToBeSaved.getEmail().equals(EMAIL)
                                       && userToBeSaved.getAge().equals(AGE) && userToBeSaved.getPassword().equals(PASSWORD)
                        )
                )
        ).thenReturn(user);

        //to be tested
        List<SeriesDTO> seriesDTOList = userService.addToFavoriteList(ID_SERIES, ID_USER);

        assertEquals(1,  seriesDTOList.size());
        assertEquals(NAME, seriesDTOList.getFirst().name());
        assertEquals(NO_OF_SEASONS, seriesDTOList.getFirst().noOfSeasons());
        assertEquals(ID_SERIES, seriesDTOList.getFirst().id());
        assertEquals(CATEGORY_TYPE, seriesDTOList.getFirst().categoryType());
    }
}