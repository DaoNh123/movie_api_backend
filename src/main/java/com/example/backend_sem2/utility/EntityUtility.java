package com.example.backend_sem2.utility;

import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.mapper.CategoryMapper;
import com.example.backend_sem2.service.interfaceService.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EntityUtility {
    private CategoryService categoryService;
    private CategoryMapper categoryMapper;

    /*  Using when convert multiple List<String> categoryList
     *  ==> create 1 Connection to Database and this connection
     * belong to upper layer */
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

    /*  Using when convert only one List<String> categoryList
     *   ==> create 1 Connection to Database  */
    public List<Category> toCategoryListRepo(
            List<String> categoryListInStringFormat
    ){
        Map<String, Category> categoryNameMapInDB = categoryService.getCategoryNameMapInDB();

        Set<Category> categorySet = new HashSet<>();
        if(!CollectionUtils.isEmpty(categoryListInStringFormat)){
            categorySet = categoryListInStringFormat.stream()
                    .map(categoryName -> {
                        Category category = categoryNameMapInDB.get(categoryName);
                        if(category != null) return category;
                        else {
                            Category newCategory = Category.builder().categoryName(categoryName).build();
                            categoryNameMapInDB.put(categoryName, newCategory);
                            return newCategory;
                        }
                    }).collect(Collectors.toSet());
        }
        List<Category> listCategory = categorySet.stream().toList();
        listCategory.forEach(category -> System.out.println(category.getId()));
        System.out.println("*** listCategory = ");
        listCategory.stream().map(categoryMapper::toDto).forEach(System.out::println);
        return listCategory;
    }
}
