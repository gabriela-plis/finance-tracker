package com.zaprogramujzycie.finanse.income;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AddIncomeDTO(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    LocalDate date,

    @NotNull
    @Min(0)
    BigDecimal amount,

    @Size(max = 100, message = "Exceeded maximum length for the description")
    String description
) { }