package com.financetracker.app.report.types;

import com.financetracker.app.expense.ExpenseEntity;
import com.financetracker.app.user.UserEntity;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.DayOfWeek;

@Builder
public record GeneralMonthlyReport(
    UserEntity user,
    DateInterval dateInterval,
    BigDecimal totalExpenses,
    ExpenseEntity largestExpense,
    BigDecimal averageWeeklyExpense,
    DateInterval weekWithHighestExpenses,
    DayOfWeek dayWithHighestAverageExpense,
    BigDecimal totalIncomes,
    BigDecimal budgetSummary
) implements Report { }
