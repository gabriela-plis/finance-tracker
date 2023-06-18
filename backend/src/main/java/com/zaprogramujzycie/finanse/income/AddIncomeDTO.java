package com.zaprogramujzycie.finanse.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AddIncomeDTO(
    LocalDateTime date,

    BigDecimal amount,

    String description
) { }