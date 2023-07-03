package com.zaprogramujzycie.finanse.income;

import com.zaprogramujzycie.finanse.utils.validation.date.ValidDateInterval;
import com.zaprogramujzycie.finanse.utils.validation.numbers.ValidNumberRange;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@ValidDateInterval(startDateField = "dateMin", endDateField = "dateMax")
@ValidNumberRange(startNumberField = "amountMin", endNumberField = "amountMax")
public record IncomeSortingCriteriaDTO(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dateMin,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dateMax,

    @Min(0)
    BigDecimal amountMin,

    @Min(0)
    BigDecimal amountMax,

    @Size(max = 64, message = "Exceeded maximum length for the keyword")
    String keyword
) { }
