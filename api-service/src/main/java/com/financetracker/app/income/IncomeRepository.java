package com.financetracker.app.income;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface IncomeRepository extends MongoRepository<Income, String> {

    Page<Income> findIncomesByUserId(String userId, Pageable pageable);

    Page<Income> findIncomesByUserIdAndDateBetweenAndAmountBetweenAndDescriptionContainingIgnoreCase(String userId, LocalDate minDate, LocalDate maxDate, BigDecimal minAmount, BigDecimal maxAmount, String keyword, Pageable pageable);

    Optional<Income> findIncomeByIdAndUserId(String incomeId, String userId);

    void deleteIncomeByIdAndUserId(String incomeId, String userId);

}
