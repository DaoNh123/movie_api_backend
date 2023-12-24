package com.example.backend_sem2.controller;

import com.example.backend_sem2.dto.CategoryDto;
import com.example.backend_sem2.service.interfaceService.CategoryService;
import jakarta.validation.Valid;
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
    public Page<CategoryDto> getPageCategoryByCondition (
            Pageable pageable,
            @RequestParam(value = "name", required = false) String name
    ){
        return categoryService.getPageCategoryByCondition(pageable, name);
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById (@PathVariable Long id)
    {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public CategoryDto saveCategory(@RequestBody String categoryName)
    {
        return categoryService.saveCategory(categoryName);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long id)
    {
        return categoryService.updateCategory(categoryDto, id);
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
