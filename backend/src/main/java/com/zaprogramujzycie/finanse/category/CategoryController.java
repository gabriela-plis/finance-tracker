package com.zaprogramujzycie.finanse.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/{userId}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addCategory(@PathVariable String userId, @RequestBody AddCategoryDTO category) {
        categoryService.add(userId, category);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
    }
}
