package com.example.backend_sem2.service.ServiceImpl;

import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.repository.CategoryRepo;
import com.example.backend_sem2.service.interfaceService.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepo categoryRepo;

    @Override
    public Page<Category> getPageCategoryByCondition(Pageable pageable, String name) {
        return categoryRepo.findPageCategoryByCondition(pageable, name);
    }

    @Override
    public Category saveCategory(String categoryName) {
        if(categoryRepo.existsByCategoryNameIgnoreCase(categoryName)){
            String message = categoryName + " is already exist!";
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, message);
        }
        return categoryRepo.save(new Category(categoryName, new ArrayList<>()));
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.getReferenceById(id);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        Category deletedCategory = categoryRepo.findById(id).orElseThrow(() -> new CustomErrorException(HttpStatus.NOT_FOUND, "Category is not exist!"));
        categoryRepo.delete(deletedCategory);
        return true;
    }
}
