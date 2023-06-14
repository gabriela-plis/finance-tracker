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
@RequestMapping("/categories")
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
    public CategoryDTO createCategory(@RequestBody AddCategoryDTO addCategoryDTO) {
        Category newCategory = categoryMapper.mapToAddCategory(addCategoryDTO);
        Category createdCategory = categoryService.save(newCategory);
        return categoryMapper.mapToCategoryDTO(createdCategory);
    }

    @ApiOperation(value = "View a list of available categories")
    @GetMapping
    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryService.findAll();
        return categories.stream()
                .map(categoryMapper::mapToCategoryDTO)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get a category by Id")
    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable String id) {
        Category category = categoryService.findById(id);
        return categoryMapper.mapToCategoryDTO(category);
    }

    @ApiOperation(value = "Update a category")
    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        Category updatedCategory = categoryMapper.mapToCategory(categoryDTO);
        Category savedCategory = categoryService.update(id, updatedCategory);
        return categoryMapper.mapToCategoryDTO(savedCategory);
    }

    @ApiOperation(value = "Delete a category")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryService.delete(id);
    }
}
