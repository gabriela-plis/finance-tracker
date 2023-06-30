package com.zaprogramujzycie.finanse.income;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AddIncomeDTO(
    LocalDate date,

    BigDecimal amount,

    String description
) { }