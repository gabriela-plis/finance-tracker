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
        return categoryRepository.findByOwner_Id(userId);
    }

    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

    public void add(String userId, AddCategoryDTO categoryToAdd) {
        Category category = categoryMapper.toEntity(categoryToAdd);
        category.setOwner(userService.getUserById(userId));

        categoryRepository.insert(category);
    }

    public Category getCategory(String categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(DocumentNotFoundException::new);
    }
}
