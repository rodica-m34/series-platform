package com.itschool.series_platform.service.impl;

import com.itschool.series_platform.entity.Series;
import com.itschool.series_platform.entity.User;
import com.itschool.series_platform.exception.SeriesNotFoundException;
import com.itschool.series_platform.exception.UserNotFoundException;
import com.itschool.series_platform.model.SeriesDTO;
import com.itschool.series_platform.model.UserDTO;
import com.itschool.series_platform.repository.SeriesRepository;
import com.itschool.series_platform.repository.UserRepository;
import com.itschool.series_platform.service.UserService;
import com.itschool.series_platform.utils.ModelConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SeriesRepository seriesRepository;

    public UserServiceImpl(UserRepository userRepository, SeriesRepository seriesRepository) {
        this.userRepository = userRepository;
        this.seriesRepository = seriesRepository;
    }

    @Override
    public UserDTO createUser(UserDTO newUserDTO) {
        User userEntity = ModelConverter.toUserEntity(newUserDTO);

        // save the new User entity in the database
        User createdUserEntity = userRepository.save(userEntity); // since the provided User entity does not have an ID, it will CREATE a new record in the database

        return ModelConverter.toUserDTO(createdUserEntity);
    }

    @Override
    public UserDTO updateUser(long id, UserDTO updatedUserDTO) {
        if (userRepository.findById(id).isEmpty()) { //in case a user with that id does not exist
            throw new UserNotFoundException("User " + id + " does not exist in db!");
        }

        User userEntity = ModelConverter.toUserEntity(updatedUserDTO);
        userEntity.setId(id);

        // save the updated User entity in the database
        User updatedUserEntity = userRepository.save(userEntity);

        return ModelConverter.toUserDTO(updatedUserEntity);
    }

    @Override
    public void deleteUser(long id) {
        if (userRepository.findById(id).isEmpty()) { //in case a user with that id does not exist
            throw new UserNotFoundException("User " + id + " does not exist in db!");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<SeriesDTO> addToFavoriteList(long idSeries, long idUser) {

        //searches in series DB for idSeries
        Series series = seriesRepository.findById(idSeries)
                .orElseThrow(() -> new SeriesNotFoundException("Series " + idSeries + " not found in db!"));
        //searches in user DB for idUser
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException("User " + idUser + " not found in db!"));
        //adds to users' list of series
        List<Series> userSeries = user.getSeries();
        userSeries.add(series);
        //save in DB
        userRepository.save(user);

        return userSeries.stream().map(ModelConverter::toSeriesDTO).toList();
    }

    @Override
    public List<SeriesDTO> getFavoriteList(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User " + id + " not found in db!"));
        List <Series> userSeries = user.getSeries();

        return userSeries.stream()
                .map(ModelConverter::toSeriesDTO).toList();
    }

    @Override
    public List<SeriesDTO> deleteFromFavoriteList(long idSeries, long idUser) {
        //search for user in DB if exists
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException("User " + idUser + " not found in db!"));
        //search for series in DB if exists
        Series series = seriesRepository.findById(idSeries)
                .orElseThrow(() -> new SeriesNotFoundException("Series " + idSeries + " not found in db!"));

        //get user's associated list of series
        List <Series> userSeries = user.getSeries();
        if (!userSeries.contains(series)){
            throw new SeriesNotFoundException("Series " + idSeries + " is not in user's favorite list");
        }
        userSeries.remove(series);
        userRepository.save(user);
        return userSeries.stream() //return updated favorite list
                .map(ModelConverter::toSeriesDTO).toList();
    }


    @Override
    public List<SeriesDTO> findSeriesByName(String name, long idUser) {
        //search for user in DB if exists
        if (userRepository.findById(idUser).isEmpty()) {
                throw new UserNotFoundException("User " + idUser + " not found in db!");
        }
        //get user's associated list of series
        List<Series> seriesList= seriesRepository.findByUsers_IdAndNameContainingIgnoreCase(idUser, name);
        if (seriesList.isEmpty()){
            throw new SeriesNotFoundException("Series " + name + " not found in db!");
        }
        return seriesList.stream().map(ModelConverter::toSeriesDTO).toList();
    }

}
