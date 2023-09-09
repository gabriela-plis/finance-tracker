package com.financetracker.app.income;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;


public interface IncomeRepository extends MongoRepository<IncomeEntity, String> {
    List<IncomeEntity> findByDateBetweenAndUserId(LocalDate startDate, LocalDate endDate, String userId);
}
