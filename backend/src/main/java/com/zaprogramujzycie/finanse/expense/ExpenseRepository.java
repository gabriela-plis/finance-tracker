package com.zaprogramujzycie.finanse.expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public interface ExpenseRepository extends MongoRepository<Expense, String> {

    Page<Expense> findByUserIdAndDateBetweenAndPriceBetweenAndCategoryAndOrderByDateDesc(String id, LocalDateTime minDate, LocalDateTime maxDate, BigDecimal minPrice, BigDecimal maxPrice, String category, Pageable pageable);

    Page<Expense> findByUserId(String id, Pageable pageable);
}
