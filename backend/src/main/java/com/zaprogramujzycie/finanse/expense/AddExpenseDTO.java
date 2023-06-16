package com.zaprogramujzycie.finanse.expense;

import com.zaprogramujzycie.finanse.category.CategoryDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AddExpenseDTO(
    LocalDateTime date,

    @Valid
    CategoryDTO category,

    @NotNull
    @Min(0)
    BigDecimal price
) {
}
