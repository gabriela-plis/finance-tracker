package com.financetracker.app.income;

import java.math.BigDecimal;
import java.util.List;

public class IncomeOperationsPerformer {

    public static BigDecimal getTotalIncomes(List<IncomeEntity> incomes) {
        return incomes.stream()
            .map(IncomeEntity::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
