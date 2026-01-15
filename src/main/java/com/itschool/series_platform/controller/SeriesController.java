package com.itschool.series_platform.controller;

import com.itschool.series_platform.model.SeriesDTO;
import com.itschool.series_platform.service.SeriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Series Manager", description = "Series Manager API that can add, edit and delete series in DB")
@RestController
@RequestMapping("series")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @Operation(summary = "Add a new series", description = "Add a new series to the database and return the created series. " +
            "Add category_id field as follows: 1.Comedy, 2.Drama, 3.Thriller, 4.SF, 5.Children's series")
    @PostMapping
    public SeriesDTO createSeries(@RequestBody SeriesDTO seriesDTO) {
        return seriesService.createSeries(seriesDTO);
    }

    @Operation(summary = "Update a series", description = "Update a series by their id and return the updated series")
    @PutMapping("{id}")
    public SeriesDTO updateSeries(@PathVariable long id, @RequestBody SeriesDTO seriesDTO){
        return seriesService.updateSeries(id, seriesDTO);
    }

    @Operation(summary = "Delete a series", description = "Delete a series by their id. An HTTP 200 OK response is returned if the series was deleted successfully")
    @DeleteMapping("{id}")
    public void deleteSeries(@PathVariable long id){
        seriesService.deleteSeries(id);
    }

    @Operation(summary = "Find all series", description = "Find all users in the database and return them in a list")
    @GetMapping
    public List<SeriesDTO> getAllSeries(){
        return seriesService.getAllSeries();
    }

    @Operation(summary = "Find series by category id", description = "Find all series by their category id: 1.Comedy, 2.Drama, 3.Thriller, 4.SF, 5.Children's series and return the list found. ")
    @GetMapping("{id}")
    public List<SeriesDTO> getByCategory (@PathVariable long id){
        return seriesService.getByCategory(id);
    }

    @Operation(summary = "Find series by name", description = "Find all series by their name and return the list found")
    @GetMapping("{name}")
    public List<SeriesDTO> getByName (@PathVariable String name){
        return seriesService.findSeriesByName(name);
    }

    @Operation
    @DeleteMapping("category/{id}")
    public void deleteCategory(@PathVariable long id){
        seriesService.deleteCategory(id);
    }
}
