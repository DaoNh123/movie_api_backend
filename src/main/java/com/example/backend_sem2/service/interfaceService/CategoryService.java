package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.dto.CategoryDto;
import com.example.backend_sem2.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    public Page<CategoryDto> getPageCategoryByCondition(Pageable pageable, String name);

    CategoryDto saveCategory(String categoryName);

    CategoryDto getCategoryById(Long id);


    boolean deleteCategoryById(Long id);

    CategoryDto updateCategory(CategoryDto categoryDto, Long id);
}
