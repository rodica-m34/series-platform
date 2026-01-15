package com.itschool.series_platform.service.impl;

import com.itschool.series_platform.entity.Category;
import com.itschool.series_platform.entity.Series;
import com.itschool.series_platform.exception.CategoryNotFoundException;
import com.itschool.series_platform.exception.SeriesNotFoundException;
import com.itschool.series_platform.model.SeriesDTO;
import com.itschool.series_platform.repository.CategoryRepository;
import com.itschool.series_platform.repository.SeriesRepository;
import com.itschool.series_platform.service.SeriesService;
import com.itschool.series_platform.utils.ModelConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesServiceImpl implements SeriesService {

    private final SeriesRepository seriesRepository;
    private final CategoryRepository categoryRepository;

    public SeriesServiceImpl(SeriesRepository seriesRepository, CategoryRepository categoryRepository) {
        this.seriesRepository = seriesRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public SeriesDTO createSeries(SeriesDTO seriesDTO) {

        Series seriesEntity = ModelConverter.toSeriesEntity(seriesDTO);

        //search category DB by category ID and returns the corresponding entity
        Category category = categoryRepository.findById(seriesDTO.category().id())
                .orElseThrow(() -> new CategoryNotFoundException("Category " + seriesDTO.category().id() + " does not exist in db!"));
        seriesEntity.setCategory(category);
        // save the new Series entity in the database
        Series createdSeriesEntity = seriesRepository.save(seriesEntity);

        return ModelConverter.toSeriesDTO(createdSeriesEntity);
    }

    @Override
    public SeriesDTO updateSeries(long id, SeriesDTO seriesDTO) {
        Series seriesEntity = ModelConverter.toSeriesEntity(seriesDTO);
        seriesEntity.setId(id); // set the ID of the Series entity to ensure we are updating the correct record
        Category category = categoryRepository.findById(seriesDTO.category().id())
                .orElseThrow(() -> new CategoryNotFoundException("Category " + seriesDTO.category().id() + " does not exist in db!"));
        seriesEntity.setCategory(category);

        // save the updated Series entity in the database
        Series updatedSeriesEntity = seriesRepository.save(seriesEntity);

        return ModelConverter.toSeriesDTO(updatedSeriesEntity);
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
        List<Series> allSeriesEntities = seriesRepository.findAll(); // retrieve all Series entities from the database

        return allSeriesEntities.stream()
                .map(ModelConverter::toSeriesDTO)
                .toList();
    }

    @Override
    public List<SeriesDTO> getByCategory(long idCategory) {
        if (categoryRepository.findById(idCategory).isEmpty()) {
            throw(new CategoryNotFoundException("Category " + idCategory + " does not exist in db!"));
        }

        List<Series> allSeriesEntities = seriesRepository.findByCategory_Id(idCategory); // retrieve all Series entities from the database

        return allSeriesEntities.stream()
                .map(ModelConverter::toSeriesDTO)
                .toList();
    }

    @Override
    public List<SeriesDTO> findSeriesByName (String name){
        List<Series> allSeriesEntities = seriesRepository.findSeriesByNameContainingIgnoreCase(name); // retrieve all Series entities from the database

        return allSeriesEntities.stream()
                .map(ModelConverter::toSeriesDTO)
                .toList();
    }

    @Override
    public void deleteCategory(long idCategory) {
       categoryRepository.deleteById(idCategory);
    }
}
