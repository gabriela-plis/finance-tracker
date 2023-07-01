package com.zaprogramujzycie.finanse.expense;

import com.zaprogramujzycie.finanse.category.CategoryDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AddExpenseDTO(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    LocalDate date,

    @Valid
    CategoryDTO category,

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal price
) {
}
