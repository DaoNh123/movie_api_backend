package com.example.backend_sem2.controller.admin;

import com.example.backend_sem2.dto.CategoryDto;
import com.example.backend_sem2.service.interfaceService.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {
    private CategoryService categoryService;

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
