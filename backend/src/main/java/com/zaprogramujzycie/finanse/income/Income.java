package com.zaprogramujzycie.finanse.income;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Income {
    @Id
    private String id;

    private LocalDateTime date;

    private BigDecimal amount;

    private String description;

    private String userId;
}
