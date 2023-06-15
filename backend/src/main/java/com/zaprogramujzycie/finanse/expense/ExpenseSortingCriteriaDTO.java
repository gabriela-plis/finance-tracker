package com.zaprogramujzycie.finanse.expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseSortingCriteriaDTO(
        LocalDateTime dateMin,

        LocalDateTime dateMax,

        BigDecimal priceMin,

        BigDecimal priceMax,

        String category
) { }
