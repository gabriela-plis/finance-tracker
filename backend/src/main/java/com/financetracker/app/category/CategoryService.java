package com.financetracker.app.category;

import com.financetracker.app.user.UserService;
import com.financetracker.app.utils.exception.custom.DocumentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserService userService;

    public List<Category> getUserCategories(String userId) {
        return categoryRepository.findCategoriesByUserId(userId);
    }

    public void deleteCategory(String categoryId, String userId) {
        categoryRepository.deleteCategoryByIdAndUserId(categoryId, userId);
    }

    public void createCategory(String userId, AddCategoryDTO categoryToAdd) {
        Category category = categoryMapper.toEntity(categoryToAdd);
        category.setUser(userService.getUserById(userId));

        categoryRepository.insert(category);
    }

    public Category getCategory(String categoryId, String userId) {
        return categoryRepository.findCategoryByIdAndUserId(categoryId, userId)
            .orElseThrow(DocumentNotFoundException::new);
    }
}
