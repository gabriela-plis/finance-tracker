package com.zaprogramujzycie.finanse.category;

import com.zaprogramujzycie.finanse.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final UserService userService;

    public List<CategoryDTO> getAll() {
        return categoryMapper.toDTOs(categoryRepository.findAll());
    }

    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

    public void add(String userId, AddCategoryDTO categoryToAdd) {
        Category category = categoryMapper.toEntity(categoryToAdd);
        category.setOwner(userService.getUserById(userId));

        categoryRepository.insert(category);
    }

}
