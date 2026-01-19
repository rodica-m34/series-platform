package com.itschool.series_platform.repository;

import com.itschool.series_platform.entity.CategoryType;
import com.itschool.series_platform.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepository extends JpaRepository<Series,Long> {
    List<Series> findByCategoryType(CategoryType category);
    List<Series> findSeriesByNameContainingIgnoreCase(String name);

    List<Series> findByUsers_IdAndNameContainingIgnoreCase(Long userId, String name);
}
