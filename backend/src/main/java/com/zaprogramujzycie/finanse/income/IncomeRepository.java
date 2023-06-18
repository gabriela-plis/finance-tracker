package com.zaprogramujzycie.finanse.income;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IncomeRepository extends MongoRepository<Income, String> {

    Page<Income> findByUser_Id(String userId, Pageable pageable);


    Page<Income> findByUser_IdAndDateBetweenAndAmountBetweenAndDescriptionContainingIgnoreCase(String userId, LocalDateTime localDateTime, LocalDateTime localDateTime1, BigDecimal bigDecimal, BigDecimal bigDecimal1, String keyword, Pageable pageable);
}
