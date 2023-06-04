package com.zaprogramujzycie.finanse.expense;

import com.zaprogramujzycie.finanse.category.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseDTO(
    String id,

    LocalDateTime date,

    Category category,

    BigDecimal price
) { }
