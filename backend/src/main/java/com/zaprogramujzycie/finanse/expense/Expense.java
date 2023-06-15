package com.zaprogramujzycie.finanse.expense;

import com.zaprogramujzycie.finanse.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document(collection = "expenses")
public class Expense {
    @Id
    private String id;

    private LocalDateTime date;

    private Category category;

    private BigDecimal price;

    @DBRef
    private String userId;
}
