package com.financetracker.app.category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

    List<Category> findCategoriesByUsersId(String userId);

    Optional<Category> findCategoryByIdAndUsersId(String categoryId, String userId);

    Optional<Category> findByName(String categoryName);

}
