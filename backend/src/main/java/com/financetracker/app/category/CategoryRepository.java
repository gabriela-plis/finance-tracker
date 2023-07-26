package com.financetracker.app.category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

    List<Category> findCategoriesByUserId(String userId);

    Optional<Category> findCategoryByIdAndUserId(String categoryId, String userId);

    void deleteCategoryByIdAndUserId(String categoryId, String userId);

}
