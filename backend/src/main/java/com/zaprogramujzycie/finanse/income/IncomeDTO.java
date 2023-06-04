package com.zaprogramujzycie.finanse.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IncomeDTO (

    String id,

    LocalDateTime date,

    BigDecimal amount,

    String description

) { }
