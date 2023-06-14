package com.zaprogramujzycie.finanse.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDTO> getAll() {
        return categoryMapper.toDTOs(categoryRepository.findAll());
    }

    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

    public void add(AddCategoryDTO category) {
        categoryRepository.insert(categoryMapper.toEntity(category));
    }
}
