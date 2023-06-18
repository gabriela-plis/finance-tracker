package com.zaprogramujzycie.finanse.income;

import com.zaprogramujzycie.finanse.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document(collection = "incomes")
public class Income {
    @Id
    private String id;

    private LocalDateTime date;

    private BigDecimal amount;

    private String description;

    @DBRef
    private User user;
}
