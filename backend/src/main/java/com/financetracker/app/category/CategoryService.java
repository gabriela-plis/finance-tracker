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
        return categoryRepository.findCategoriesByUsersId(userId);
    }

    public void deleteCategory(String categoryId, String userId) {
        Category category = categoryRepository.findCategoryByIdAndUsersId(categoryId, userId)
            .orElseThrow(DocumentNotFoundException::new);

        if (category.getUsers().size() == 1) {
            categoryRepository.delete(category);
        } else {
            category.getUsers().remove(userService.getUserById(userId));
            categoryRepository.save(category);
        }
    }

    public void createCategory(String userId, AddCategoryDTO categoryToAdd) {
        Category category = categoryRepository.findByName(categoryToAdd.name())
            .orElse(null);

        if (category != null) {
            if (category.getUsers().stream().anyMatch(user -> user.getId().equals(userId))) return;
            category.getUsers().add(userService.getUserById(userId));
        }

        Category newCategory = categoryMapper.toEntity(categoryToAdd);
        newCategory.getUsers().add(userService.getUserById(userId));

        categoryRepository.insert(newCategory);
    }

    public Category getCategory(String categoryId, String userId) {
        return categoryRepository.findCategoryByIdAndUsersId(categoryId, userId)
            .orElseThrow(DocumentNotFoundException::new);
    }
}
