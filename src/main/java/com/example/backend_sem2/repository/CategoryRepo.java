package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM categories c WHERE (CAST(:name AS TEXT) IS NULL OR c.category_name ILIKE '%' || :name || '$')",
    nativeQuery = true)
    Page<Category> findPageCategoryByCondition(Pageable pageable, String name);

    Boolean existsByCategoryNameIgnoreCase(String name);

    @Query(value = "FROM Category c")
    Set<Category> getAllCategorySet();

    Optional<Category> findByCategoryName (String categoryName);
}
