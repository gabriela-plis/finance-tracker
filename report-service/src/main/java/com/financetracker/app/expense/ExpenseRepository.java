package com.financetracker.app.expense;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends MongoRepository<ExpenseEntity, String> {
    List<ExpenseEntity> findByDateBetweenAndUserId(LocalDate startDate, LocalDate endDate, String userId);
}
