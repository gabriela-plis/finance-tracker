package com.zaprogramujzycie.finanse.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IncomeSortingCriteriaDTO(

        LocalDateTime dateMin,

        LocalDateTime dateMax,

        BigDecimal amountMin,

        BigDecimal amountMax,

        String keyword
) { }
