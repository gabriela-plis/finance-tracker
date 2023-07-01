package com.zaprogramujzycie.finanse.expense;

import com.zaprogramujzycie.finanse.utils.validation.date.ValidDateInterval;
import com.zaprogramujzycie.finanse.utils.validation.numbers.ValidNumberRange;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ValidDateInterval(startDateField = "dateMin", endDateField = "dateMax")
@ValidNumberRange(startNumberField = "priceMin", endNumberField = "priceMax")
public record ExpenseSortingCriteriaDTO(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dateMin,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dateMax,

    @Min(0)
    BigDecimal priceMin,

    @Min(0)
    BigDecimal priceMax,

    List<String> categoryIds
) { }
