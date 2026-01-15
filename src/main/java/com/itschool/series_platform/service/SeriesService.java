package com.itschool.series_platform.service;

import com.itschool.series_platform.model.SeriesDTO;

import java.util.List;

public interface SeriesService {
    SeriesDTO createSeries (SeriesDTO seriesDTO);
    SeriesDTO updateSeries(long id, SeriesDTO seriesDTO);
    void deleteSeries(long id);
    List<SeriesDTO> getAllSeries();
    List<SeriesDTO> getByCategory (long idCategory);
    List<SeriesDTO> findSeriesByName (String name);
    void deleteCategory(long idCategory);
}
