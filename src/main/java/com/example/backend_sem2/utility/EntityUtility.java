package com.example.backend_sem2.utility;

import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.repository.CategoryRepo;
import com.example.backend_sem2.service.interfaceService.CategoryService;
import com.example.backend_sem2.service.serviceImpl.CategoryServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EntityUtility {
    private CategoryService categoryService;

    public List<Category> toCategoryListRepo(
            List<String> categoryListInStringFormat,
            Map<String, Category> CategoryNameMapInDB
    ){
        Set<Category> categorySet = new HashSet<>();
        if(!CollectionUtils.isEmpty(categoryListInStringFormat)){
            categorySet = categoryListInStringFormat.stream()
                    .map(categoryName -> {
                        Category category = CategoryNameMapInDB.get(categoryName);
                        if(category != null) return category;
                        else {
                            Category newCategory = Category.builder().categoryName(categoryName).build();
                            CategoryNameMapInDB.put(categoryName, newCategory);
                            return newCategory;
                        }
                    }).collect(Collectors.toSet());
        }
        return categorySet.stream().toList();
    }
}
