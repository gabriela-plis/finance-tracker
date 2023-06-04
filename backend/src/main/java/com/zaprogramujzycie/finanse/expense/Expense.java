package com.zaprogramujzycie.finanse.expense;

import com.zaprogramujzycie.finanse.category.Category;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Expense {
    @Id
    private String id;

    private LocalDateTime date;

    private Category category;

    private BigDecimal price;

    private String userId;

}
