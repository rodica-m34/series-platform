package com.itschool.series_platform.service;

import com.itschool.series_platform.model.SeriesDTO;
import com.itschool.series_platform.model.UserDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {

    UserDTO createUser(UserDTO newUserDTO);
    UserDTO updateUser(long id, UserDTO updatedUserDTO);
    void deleteUser(long id);
    List<SeriesDTO> addToFavoriteList(long idSeries, long idUser);
    List<SeriesDTO> getFavoriteList (long id);
    List<SeriesDTO> deleteFromFavoriteList(long idSeries, long idUser);
    List <SeriesDTO> findSeriesByName(String name, long idUser);
}