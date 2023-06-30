package com.zaprogramujzycie.finanse.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ExpenseSortingCriteriaDTO(
    LocalDate dateMin,

    LocalDate dateMax,

    BigDecimal priceMin,

    BigDecimal priceMax,

    List<String> categoryIds
) { }
