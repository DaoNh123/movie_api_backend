package com.example.backend_sem2.controller;

import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.service.interfaceService.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping(value = {"", "/"})
    public Page<Category> getPageCategoryByCondition (
            Pageable pageable,
            @RequestParam(value = "name", required = false) String name
    ){
        if(name == null) name = "";
        return categoryService.getPageCategoryByCondition(pageable, name);
    }

    @GetMapping("/{id}")
    public Category getCategoryById (@PathVariable Long id)
    {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public Category saveCategory(@RequestBody String categoryName)
    {
        return categoryService.saveCategory(categoryName);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@RequestBody Category category, @PathVariable Long id)
    {
        category.setId(id);
        return categoryService.saveCategory(category);
    }



    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id)
    {
        if(categoryService.deleteCategoryById(id)){
            return "Delete Successful!";
        }
        return "Delete Fail!";
    }
}
