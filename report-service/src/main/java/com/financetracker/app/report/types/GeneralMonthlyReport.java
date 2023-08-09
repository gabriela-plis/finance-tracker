package com.financetracker.app.report.types;

import com.financetracker.app.expense.ExpenseEntity;
import com.financetracker.app.user.UserEntity;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.DayOfWeek;

@Builder
public record GeneralMonthlyReport(
    UserEntity user,
    DateRange dateRange,
    BigDecimal totalExpenses,
    ExpenseEntity largestExpense,
    BigDecimal averageWeeklyExpense,
    DateRange weekWithHighestExpenses,
    DayOfWeek dayWithHighestAverageExpense,
    BigDecimal totalIncome,
    BigDecimal budgetSummary
) implements Report { }
