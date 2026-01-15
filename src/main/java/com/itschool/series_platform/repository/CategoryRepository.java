package com.itschool.series_platform.repository;

import com.itschool.series_platform.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Long id(long id);
}
