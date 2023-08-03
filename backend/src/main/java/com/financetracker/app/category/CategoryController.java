package com.financetracker.app.category;
import com.financetracker.app.security.authentication.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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
    public CategoryDTO getCategory(@PathVariable String categoryId, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        return categoryMapper.toDTO(categoryService.getCategory(categoryId, userId));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createCategory(@RequestBody @Valid AddCategoryDTO category, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        categoryService.createCategory(userId, category);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String categoryId, Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        categoryService.deleteCategory(categoryId, userId);
    }
}
