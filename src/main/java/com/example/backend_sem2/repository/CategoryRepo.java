package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM categories c WHERE (:name IS NULL OR c.category_name ILIKE '%' || :name || '$')",
    nativeQuery = true)
    Page<Category> findPageCategoryByCondition(Pageable pageable, String name);

    Boolean existsByCategoryNameIgnoreCase(String name);
}
