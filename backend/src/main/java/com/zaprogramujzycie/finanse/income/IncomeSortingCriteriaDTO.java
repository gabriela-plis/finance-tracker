package com.zaprogramujzycie.finanse.income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record IncomeSortingCriteriaDTO(
        LocalDate dateMin,

        LocalDate dateMax,

        BigDecimal priceMin,

        BigDecimal priceMax,

        List<String> categoryIds
) { }
