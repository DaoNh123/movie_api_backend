package com.example.backend_sem2.controller.user;

import com.example.backend_sem2.dto.CategoryDto;
import com.example.backend_sem2.service.interfaceService.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
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

}
