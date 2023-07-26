package com.financetracker.app.expense;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ExpenseRepository extends MongoRepository<Expense, String> {

    @Query("{'user._id': ?0, 'date': { '$gte':  ?1, '$lte':  ?2}, 'price': { '$gte':  ?3 , '$lte': ?4  } }")
    Page<Expense> findExpensesByUserIdAndDateBetweenAndPriceBetween(
        String userId,
        LocalDate minDate,
        LocalDate maxDate,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Pageable pageable
    );

    @Query("{'user._id': ?0, 'date': { '$gte':  ?1, '$lte':  ?2}, 'price': { '$gte':  ?3 , '$lte': ?4  }, 'category.$id': { '$in': ?5 } }")
    Page<Expense> findExpensesByUserIdAndDateBetweenAndPriceBetweenAndCategoryIdIn(
        String userId,
        LocalDate minDate,
        LocalDate maxDate,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        List<ObjectId> categoryIds,
        Pageable pageable
    );

    Page<Expense> findExpensesByUserId(String userId, Pageable pageable);

    Optional<Expense> findExpenseByIdAndUserId(String expenseId, String userId);

    void deleteExpenseByIdAndUserId(String expenseId, String userId);

}
