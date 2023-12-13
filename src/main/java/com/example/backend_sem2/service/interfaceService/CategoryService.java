package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    public Page<Category> getPageCategoryByCondition(Pageable pageable, String name);

    Category saveCategory(String categoryName);

    Category getCategoryById(Long id);

    Category saveCategory(Category category);

    boolean deleteCategoryById(Long id);
}
