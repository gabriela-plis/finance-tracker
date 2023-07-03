package com.zaprogramujzycie.finanse.income;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeDTO (
    @NotBlank
    String id,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    LocalDate date,

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal amount,

    @Size(max = 100, message = "Exceeded maximum length for the description")
    String description
) { }
