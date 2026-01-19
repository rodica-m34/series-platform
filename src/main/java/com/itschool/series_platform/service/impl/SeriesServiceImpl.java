package com.itschool.series_platform.service.impl;

import com.itschool.series_platform.entity.CategoryType;
import com.itschool.series_platform.entity.Series;
import com.itschool.series_platform.exception.SeriesNotFoundException;
import com.itschool.series_platform.model.SeriesDTO;
import com.itschool.series_platform.repository.SeriesRepository;
import com.itschool.series_platform.service.SeriesService;
import com.itschool.series_platform.utils.ModelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SeriesServiceImpl implements SeriesService {

    private final SeriesRepository seriesRepository;
    Logger LOGGER = LoggerFactory.getLogger(SeriesServiceImpl.class);

    public SeriesServiceImpl(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @Override
    public SeriesDTO createSeries(SeriesDTO seriesDTO) {
        Series seriesEntity = ModelConverter.toSeriesEntity(seriesDTO);
        seriesRepository.save(seriesEntity);
        return ModelConverter.toSeriesDTO(seriesEntity);
    }

    @Override
    public SeriesDTO updateSeries(long id, SeriesDTO seriesDTO) {
        if (seriesRepository.findById(id).isEmpty()) { //in case a series with that id does not exist
            throw new SeriesNotFoundException("Series " + id + " does not exist in db!");
        }
        Series seriesEntity = ModelConverter.toSeriesEntity(seriesDTO);
        seriesEntity.setId(id); // set the ID of the Series entity
        seriesRepository.save(seriesEntity);
        return ModelConverter.toSeriesDTO(seriesEntity);
    }

    @Override
    public void deleteSeries(long id) {
        if (seriesRepository.findById(id).isEmpty()) { //in case a series with that id does not exist
            throw new SeriesNotFoundException("Series " + id + " does not exist in db!");
        }
        seriesRepository.deleteById(id);
    }

    @Override
    public List<SeriesDTO> getAllSeries() {
        List<Series> allSeriesEntities = seriesRepository.findAll();

        return allSeriesEntities.stream()
                .map(ModelConverter::toSeriesDTO)
                .toList();
    }

    @Override
    public List<SeriesDTO> getByCategory(CategoryType categoryType) {
        List<Series> allSeriesEntities = seriesRepository.findByCategoryType(categoryType);
        if (allSeriesEntities.isEmpty()) {
            LOGGER.warn("Currently there are no series found for the category: " + categoryType);
            return Collections.emptyList();
        }

        return  allSeriesEntities.stream()
                .map(ModelConverter::toSeriesDTO)
                .toList();
    }

    @Override
    public List<SeriesDTO> findSeriesByName (String name){
        List<Series> allSeriesEntities = seriesRepository.findSeriesByNameContainingIgnoreCase(name);
        if (allSeriesEntities.isEmpty()) {
            LOGGER.warn("Currently there are no series found with this name: " + name);
            return Collections.emptyList();
        }
        return allSeriesEntities.stream()
                .map(ModelConverter::toSeriesDTO)
                .toList();
    }
}
