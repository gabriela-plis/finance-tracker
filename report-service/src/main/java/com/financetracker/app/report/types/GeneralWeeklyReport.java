package com.financetracker.app.report.types;

import com.financetracker.app.expense.ExpenseEntity;
import com.financetracker.app.user.UserEntity;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record GeneralWeeklyReport (
    UserEntity user,
    DateRange dateRange,
    BigDecimal totalExpenses,
    ExpenseEntity largestExpense,
    BigDecimal averageDailyExpense,
    BigDecimal totalIncomes,
    BigDecimal budgetSummary
) implements Report { }
