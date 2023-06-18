package com.zaprogramujzycie.finanse.category;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "Category Management")
@RestController

@RequestMapping("users/{userId}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getUserCategories(@PathVariable String userId) {
        return categoryService.getUserCategories(userId);
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO getCategory(@PathVariable String categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @ApiOperation(value = "Add a category")
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
