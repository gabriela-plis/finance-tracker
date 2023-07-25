package com.financetracker.app.category;
import com.financetracker.app.security.authentication.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Api(value = "Category Management")
@RestController
@RequestMapping("/users/me/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final AuthenticationService authenticationService;

    @GetMapping
    public List<CategoryDTO> getUserCategories(Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        return categoryMapper.toDTOs(categoryService.getUserCategories(userId));
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO getCategory(@PathVariable String categoryId) {
        return categoryMapper.toDTO(categoryService.getCategory(categoryId));
    }

  @ApiOperation(value = "Add a category")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addCategory(@RequestBody AddCategoryDTO category, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        categoryService.add(userId, category);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
    }
}
