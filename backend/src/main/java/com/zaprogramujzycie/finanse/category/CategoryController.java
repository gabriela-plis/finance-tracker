package com.zaprogramujzycie.finanse.category;
import com.zaprogramujzycie.finanse.category.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Category Management")
@RestController

@RequestMapping("users/{userId}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
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
