package com.zaprogramujzycie.finanse.income;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeSortingCriteriaDTO(

        LocalDate dateMin,

        LocalDate dateMax,

        BigDecimal amountMin,

        BigDecimal amountMax,

        String keyword
) { }
