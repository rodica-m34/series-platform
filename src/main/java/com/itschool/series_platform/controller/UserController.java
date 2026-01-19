package com.itschool.series_platform.controller;

import com.itschool.series_platform.model.SeriesDTO;
import com.itschool.series_platform.model.UserDTO;
import com.itschool.series_platform.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Manager", description = "User Manager API that manipulates operations related to an user")
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Add a new user", description = "Add a new user to the database and return the created user")
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @Operation (summary = "Update an user", description = "Updates information related to an user and returns updated user")
    @PutMapping("{id}")
    public UserDTO updateUser(@PathVariable long id, @RequestBody UserDTO updatedUserDTO){
        return userService.updateUser(id, updatedUserDTO);
    }

    @Operation (summary = "Add a series to an user's list", description = "Add a series to an indicated user's favorite list")
    @PutMapping("/{idSeries}/{idUser}")
    public List<SeriesDTO> addToFavoriteList(@PathVariable long idSeries, @PathVariable long idUser){
        return userService.addToFavoriteList(idSeries, idUser);
    }

    @Operation (summary = "Find user's list of series", description = "Find all series in user's favorite list and return them in a list")
    @GetMapping("{idUser}")
    public List<SeriesDTO> getFavoriteList (@PathVariable long idUser){
        return userService.getFavoriteList(idUser);
    }

    @Operation (summary = "Remove a series from user's list of series", description = "Remove a series from user's favorite list and return the new list of remaining series")
    @DeleteMapping("/{idSeries}/{idUser}")
    public List<SeriesDTO> removeFromFavoriteList (@PathVariable long idSeries, @PathVariable long idUser){
        return userService.deleteFromFavoriteList(idSeries, idUser);
    }

    @Operation (summary = "Find series by name", description = "Find all series by name in user's favorite list and return them in a list")
    @GetMapping("/{idUser}/{name}")
    public List<SeriesDTO> findSeriesByName(@PathVariable String name, @PathVariable long idUser){
        return userService.findSeriesByName(name, idUser);
    }

    @Operation(summary = "Delete a user", description = "Delete a user by their id. An HTTP 200 OK response is returned if the user was deleted successfully")
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }
}
