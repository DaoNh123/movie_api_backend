package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.dto.CategoryDto;
import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.mapper.CategoryMapper;
import com.example.backend_sem2.repository.CategoryRepo;
import com.example.backend_sem2.service.interfaceService.CategoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepo categoryRepo;
    private CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> getPageCategoryByCondition(Pageable pageable, String name) {
        return categoryRepo.findPageCategoryByCondition(pageable, name).map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto saveCategory(String categoryName) {
        if (categoryRepo.existsByCategoryNameIgnoreCase(categoryName)) {
            String message = categoryName + " is already exist!";
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, message);
        }
        return categoryMapper.toDto(categoryRepo.save(Category.builder().categoryName(categoryName).build()));
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryMapper.toDto(categoryRepo.findById(id).get());
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category updatedCategory = categoryRepo.findById(id)
                .orElseThrow(() -> {
                    return new CustomErrorException(HttpStatus.BAD_REQUEST, "Category is not exist!");
                });
        categoryMapper.updateCategory(categoryDto, updatedCategory);
        try {
            categoryRepo.saveAndFlush(updatedCategory);
        } catch (Exception e) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        Category deletedCategory = categoryRepo.findById(id).orElseThrow(() -> new CustomErrorException(HttpStatus.NOT_FOUND, "Category is not exist!"));
        categoryRepo.delete(deletedCategory);
        return true;
    }

    @Override
    public Map<String, Category> getCategoryNameMapInDB() {
        Set<Category> allCategoryInDB = categoryRepo.getAllCategorySet();
        System.out.println("*** ***");
        allCategoryInDB.forEach(category -> System.out.println(category.getId()));

        Map<String, Category> nameCategoryMap = new HashMap<>();
        if(allCategoryInDB != null){
            nameCategoryMap = allCategoryInDB.stream()
                    .collect(Collectors.toMap(Category::toString, Function.identity()));
        }
        return nameCategoryMap;
    }
}
