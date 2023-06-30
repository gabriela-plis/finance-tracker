package com.zaprogramujzycie.finanse.income;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IncomeRepository extends MongoRepository<Income, String> {

    Page<Income> findByUser_Id(String userId, Pageable pageable);


    Page<Income> findByUser_IdAndDateBetweenAndAmountBetweenAndDescriptionContainingIgnoreCase(String userId, LocalDate minDate, LocalDate maxDate, BigDecimal minAmount, BigDecimal maxAmount, String keyword, Pageable pageable);
}
